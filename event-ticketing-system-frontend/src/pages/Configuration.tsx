import { useState } from 'react';
import { Configuration } from '../types';
import { API_BASE_URL } from '../config';

const ConfigurationPage = () => {
    const [config, setConfig] = useState<Configuration>({
        maxTicketCapacity: 0,
        totalTickets: 0,
        ticketReleaseRate: 0,
        customerRetrievalRate: 0
    });
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

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

        setLoading(true);
        setMessage('');
        
        try {
            const response = await fetch(`${API_BASE_URL}/api/configuration/set`, {
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
            setMessage('Error setting configuration. Is the backend running?');
        } finally {
            setLoading(false);
        }
    };

    const handleLoadConfiguration = async () => {
        setLoading(true);
        setMessage('');

        try {
            // First load the configuration
            const loadResponse = await fetch(`${API_BASE_URL}/api/configuration/load`, {
                method: 'POST',
            });

            if (!loadResponse.ok) {
                // If loading fails, try getting existing config anyway (maybe it's already loaded)
                console.warn('Load endpoint failed, trying get endpoint...');
            }

            // Then get the loaded configuration
            const getResponse = await fetch(`${API_BASE_URL}/api/configuration/get`, {
                method: 'POST',
            });

            if (getResponse.ok) {
                const loadedConfig = await getResponse.json();
                setConfig(loadedConfig);
                setMessage('Configuration loaded successfully!');
                setError('');
            } else {
                setMessage('Failed to get configuration');
            }
        } catch (error) {
            setMessage('Error loading configuration. Is the backend running?');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="p-8 max-w-md mx-auto bg-accent-light rounded-lg shadow-lg">
            <h1 className="text-2xl font-bold text-primary mb-6">Configuration</h1>
            <div className="space-y-4">
                <div>
                    <label className="block text-primary-light mb-1">Max Ticket Capacity:</label>
                    <input
                        type="number"
                        name="maxTicketCapacity"
                        value={config.maxTicketCapacity}
                        onChange={handleInputChange}
                        className="w-full p-2 border rounded focus:outline-none focus:border-accent"
                        min="0"
                    />
                </div>
                <div>
                    <label className="block text-primary-light mb-1">Total Tickets:</label>
                    <input
                        type="number"
                        name="totalTickets"
                        value={config.totalTickets}
                        onChange={handleInputChange}
                        className="w-full p-2 border rounded focus:outline-none focus:border-accent"
                        min="0"
                        max={config.maxTicketCapacity}
                    />
                </div>
                <div>
                    <label className="block text-primary-light mb-1">Ticket Release Rate:</label>
                    <input
                        type="number"
                        name="ticketReleaseRate"
                        value={config.ticketReleaseRate}
                        onChange={handleInputChange}
                        className="w-full p-2 border rounded focus:outline-none focus:border-accent"
                        min="0"
                    />
                </div>
                <div>
                    <label className="block text-primary-light mb-1">Ticket Retrieval Rate:</label>
                    <input
                        type="number"
                        name="customerRetrievalRate"
                        value={config.customerRetrievalRate}
                        onChange={handleInputChange}
                        className="w-full p-2 border rounded focus:outline-none focus:border-accent"
                        min="0"
                    />
                </div>
                
                {error && (
                    <div className="p-2 text-red-600 bg-red-100 rounded text-sm">
                        {error}
                    </div>
                )}
                
                <button
                    onClick={handleSetConfiguration}
                    className={`w-full py-2 rounded transition-colors flex justify-center items-center ${
                        error || loading 
                            ? 'bg-gray-400 cursor-not-allowed' 
                            : 'bg-accent hover:bg-primary-light text-white'
                    }`}
                    disabled={!!error || loading}
                >
                    {loading ? (
                        <span className="inline-block w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin mr-2"></span>
                    ) : null}
                    {loading ? 'Saving...' : 'Set Configuration'}
                </button>
                
                <button
                    onClick={handleLoadConfiguration}
                    className={`w-full py-2 rounded transition-colors flex justify-center items-center ${
                        loading
                            ? 'bg-gray-300 cursor-not-allowed text-gray-500'
                            : 'bg-primary-light text-white hover:bg-primary'
                    }`}
                    disabled={loading}
                >
                    {loading ? 'Loading...' : 'Load Configuration'}
                </button>
                
                {message && (
                    <div className={`mt-4 p-2 text-center rounded text-white ${
                        message.toLowerCase().includes('fail') || message.toLowerCase().includes('error')
                            ? 'bg-red-600'
                            : 'bg-green-700'
                    }`}>
                        {message}
                    </div>
                )}
            </div>
        </div>
    );
};

export default ConfigurationPage;
