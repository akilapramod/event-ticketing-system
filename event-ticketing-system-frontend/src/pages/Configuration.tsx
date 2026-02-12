import { useState } from 'react';
import { Configuration } from '../types';

const ConfigurationPage = () => {
    const [config, setConfig] = useState<Configuration>({
        maxTicketCapacity: 0,
        totalTickets: 0,
        ticketReleaseRate: 0,
        customerRetrievalRate: 0
    });
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        const numValue = parseInt(value);

        if (numValue >= 0 || value === '') {
            const newConfig = {
                ...config,
                [name]: value === '' ? 0 : numValue
            };

            // Validate total tickets against max capacity
            if (name === 'totalTickets' && numValue > config.maxTicketCapacity) {
                setError('Total tickets cannot exceed maximum capacity');
            } else if (name === 'maxTicketCapacity' && config.totalTickets > numValue) {
                setError('Maximum capacity cannot be less than total tickets');
            } else {
                setError('');
            }

            setConfig(newConfig);
        }
    };

    const handleSetConfiguration = async () => {
        // Validate before sending
        if (config.totalTickets > config.maxTicketCapacity) {
            setError('Total tickets cannot exceed maximum capacity');
            return;
        }

        try {
            const response = await fetch('/api/configuration/set', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(config),
            });

            if (response.ok) {
                setMessage('Configuration set successfully!');
                setError('');
            } else {
                setMessage('Failed to set configuration');
            }
        } catch (error) {
            setMessage('Error setting configuration');
        }
    };

    const handleLoadConfiguration = async () => {
        try {
            // First load the configuration
            const loadResponse = await fetch('/api/configuration/load', {
                method: 'POST',
            });

            if (!loadResponse.ok) {
                setMessage('Failed to load configuration');
                return;
            }

            // Then get the loaded configuration
            const getResponse = await fetch('/api/configuration/get', {
                method: 'POST',
            });

            if (getResponse.ok) {
                const loadedConfig = await getResponse.json();
                setConfig(loadedConfig);
                setMessage('Configuration loaded successfully!');
                setError('');
            } else {
                setMessage('Failed to get loaded configuration');
            }
        } catch (error) {
            setMessage('Error loading configuration');
        }
    };

    return (
        <div className="p-8 max-w-md mx-auto bg-[#E1FFBB] rounded-lg shadow-lg">
            <h1 className="text-2xl font-bold text-[#001A6E] mb-6">Configuration</h1>
            <div className="space-y-4">
                <div>
                    <label className="block text-[#074799] mb-1">Max Ticket Capacity:</label>
                    <input
                        type="number"
                        name="maxTicketCapacity"
                        value={config.maxTicketCapacity}
                        onChange={handleInputChange}
                        className="w-full p-2 border rounded focus:outline-none focus:border-[#009990]"
                        min="0"
                    />
                </div>
                <div>
                    <label className="block text-[#074799] mb-1">Total Tickets:</label>
                    <input
                        type="number"
                        name="totalTickets"
                        value={config.totalTickets}
                        onChange={handleInputChange}
                        className="w-full p-2 border rounded focus:outline-none focus:border-[#009990]"
                        min="0"
                        max={config.maxTicketCapacity}
                    />
                </div>
                <div>
                    <label className="block text-[#074799] mb-1">Ticket Release Rate:</label>
                    <input
                        type="number"
                        name="ticketReleaseRate"
                        value={config.ticketReleaseRate}
                        onChange={handleInputChange}
                        className="w-full p-2 border rounded focus:outline-none focus:border-[#009990]"
                        min="0"
                    />
                </div>
                <div>
                    <label className="block text-[#074799] mb-1">Ticket Retrieval Rate:</label>
                    <input
                        type="number"
                        name="customerRetrievalRate"
                        value={config.customerRetrievalRate}
                        onChange={handleInputChange}
                        className="w-full p-2 border rounded focus:outline-none focus:border-[#009990]"
                        min="0"
                    />
                </div>
                {error && (
                    <div className="p-2 text-red-600 bg-red-100 rounded">
                        {error}
                    </div>
                )}
                <button
                    onClick={handleSetConfiguration}
                    className={`w-full ${error ? 'bg-gray-400 cursor-not-allowed' : 'bg-[#009990] hover:bg-[#074799]'} text-white py-2 rounded transition-colors`}
                    disabled={!!error}
                >
                    Set Configuration
                </button>
                <button
                    onClick={handleLoadConfiguration}
                    className="w-full bg-[#074799] text-white py-2 rounded hover:bg-[#001A6E] transition-colors"
                >
                    Load Configuration
                </button>
                {message && (
                    <div className="mt-4 p-2 text-center rounded bg-green-700">
                        {message}
                    </div>
                )}
            </div>
        </div>
    );
};

export default ConfigurationPage;
