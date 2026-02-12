import { useState } from 'react';
import ConfigurationPage from './pages/Configuration';
import ControlPanel from './pages/ControlPanel';

function App() {
  const [currentPage, setCurrentPage] = useState<'config' | 'control'>('config');

  return (
    <div className="min-h-screen bg-gray-100">
      <nav className="bg-primary p-4">
        <div className="max-w-4xl mx-auto flex gap-4">
          <button
            onClick={() => setCurrentPage('config')}
            className={`px-4 py-2 rounded ${
              currentPage === 'config'
                ? 'bg-accent-light text-primary'
                : 'text-white hover:bg-primary-light'
            }`}
          >
            Configuration
          </button>
          <button
            onClick={() => setCurrentPage('control')}
            className={`px-4 py-2 rounded ${
              currentPage === 'control'
                ? 'bg-accent-light text-primary'
                : 'text-white hover:bg-primary-light'
            }`}
          >
            Control Panel
          </button>
        </div>
      </nav>

      <main className="max-w-4xl mx-auto p-4">
        {currentPage === 'config' ? <ConfigurationPage /> : <ControlPanel />}
      </main>
    </div>
  );
}

export default App;