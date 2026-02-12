import { useState } from 'react';

const ControlPanel = () => {
    const [message, setMessage] = useState('');

    const handleStartSystem = async () => {
        try {
            const response = await fetch('/api/configuration/start', {
                method: 'POST',
            });
            
            if (response.ok) {
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
            const response = await fetch('/api/configuration/stop', {
                method: 'POST',
            });
            
            if (response.ok) {
                setMessage('System stopped successfully!');
            } else {
                setMessage('Failed to stop system');
            }
        } catch (error) {
            setMessage('Error stopping system');
        }
    };

    return (
        <div className="p-8 max-w-md mx-auto bg-[#E1FFBB] rounded-lg shadow-lg">
            <h1 className="text-2xl font-bold text-[#001A6E] mb-6">Control Panel</h1>
            <div className="space-y-4">
                <button
                    onClick={handleStartSystem}
                    className="w-full bg-[#009990] text-white py-3 rounded hover:bg-[#074799] transition-colors"
                >
                    Start System
                </button>
                <button
                    onClick={handleStopSystem}
                    className="w-full bg-[#074799] text-white py-3 rounded hover:bg-[#001A6E] transition-colors"
                >
                    Stop System
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

export default ControlPanel;
