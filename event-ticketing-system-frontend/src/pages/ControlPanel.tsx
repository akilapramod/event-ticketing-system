import { useState, useEffect, useRef } from 'react';
import { API_BASE_URL } from '../config';

const ControlPanel = () => {
    const [message, setMessage] = useState('');
    const [isRunning, setIsRunning] = useState(false);
    const [ticketCount, setTicketCount] = useState<number | null>(null);
    const pollingRef = useRef<ReturnType<typeof setInterval> | null>(null);

    // Fetch ticket count once on mount to show initial state
    useEffect(() => {
        fetchTicketCount();
    }, []);

    // Start/stop polling when system state changes
    useEffect(() => {
        if (isRunning) {
            // Poll every second while system is running
            pollingRef.current = setInterval(fetchTicketCount, 1000);
        } else {
            if (pollingRef.current) {
                clearInterval(pollingRef.current);
                pollingRef.current = null;
            }
        }
        return () => {
            if (pollingRef.current) {
                clearInterval(pollingRef.current);
            }
        };
    }, [isRunning]);

    const fetchTicketCount = async () => {
        try {
            const response = await fetch(`${API_BASE_URL}/api/tickets/available`);
            if (response.ok) {
                const count = await response.json();
                setTicketCount(count);
            }
        } catch {
            // Silently fail — don't spam error messages during polling
        }
    };

    const handleStartSystem = async () => {
        try {
            const response = await fetch(`${API_BASE_URL}/api/configuration/start`, {
                method: 'POST',
            });

            if (response.ok) {
                setIsRunning(true);
                setMessage('System started successfully!');
            } else {
                setMessage('Failed to start system');
            }
        } catch (error) {
            setMessage('Error starting system');
        }
    };

    const handleStopSystem = async () => {
        try {
            const response = await fetch(`${API_BASE_URL}/api/configuration/stop`, {
                method: 'POST',
            });

            if (response.ok) {
                setIsRunning(false);
                setMessage('System stopped successfully!');
                // Fetch final count after stopping
                fetchTicketCount();
            } else {
                setMessage('Failed to stop system');
            }
        } catch (error) {
            setMessage('Error stopping system');
        }
    };

    return (
        <div className="p-8 max-w-md mx-auto bg-accent-light rounded-lg shadow-lg">
            <h1 className="text-2xl font-bold text-primary mb-6">Control Panel</h1>

            {/* System Status */}
            <div className="mb-6 p-4 rounded-lg bg-white shadow-sm">
                <div className="flex items-center justify-between mb-2">
                    <span className="text-sm font-medium text-gray-600">System Status</span>
                    <span className={`inline-flex items-center px-3 py-1 rounded-full text-sm font-semibold ${
                        isRunning
                            ? 'bg-green-100 text-green-800'
                            : 'bg-gray-100 text-gray-600'
                    }`}>
                        <span className={`w-2 h-2 rounded-full mr-2 ${
                            isRunning ? 'bg-green-500 animate-pulse' : 'bg-gray-400'
                        }`}></span>
                        {isRunning ? 'Running' : 'Stopped'}
                    </span>
                </div>
            </div>

            {/* Ticket Counter */}
            <div className="mb-6 p-6 rounded-lg bg-white shadow-sm text-center">
                <span className="text-sm font-medium text-gray-600 block mb-1">Available Tickets</span>
                <span className="text-5xl font-bold text-primary">
                    {ticketCount !== null ? ticketCount : '—'}
                </span>
                {isRunning && (
                    <span className="text-xs text-gray-400 block mt-2">Updates every second</span>
                )}
            </div>

            {/* Controls */}
            <div className="space-y-4">
                <button
                    onClick={handleStartSystem}
                    disabled={isRunning}
                    className={`w-full py-3 rounded text-white transition-colors ${
                        isRunning
                            ? 'bg-gray-400 cursor-not-allowed'
                            : 'bg-accent hover:bg-primary-light'
                    }`}
                >
                    Start System
                </button>
                <button
                    onClick={handleStopSystem}
                    disabled={!isRunning}
                    className={`w-full py-3 rounded text-white transition-colors ${
                        !isRunning
                            ? 'bg-gray-400 cursor-not-allowed'
                            : 'bg-primary-light hover:bg-primary'
                    }`}
                >
                    Stop System
                </button>
                {message && (
                    <div className={`mt-4 p-2 text-center rounded text-white ${
                        message.includes('Error') || message.includes('Failed')
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

export default ControlPanel;
