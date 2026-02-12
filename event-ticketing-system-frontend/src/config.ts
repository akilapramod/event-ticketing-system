/**
 * Application configuration.
 * 
 * In development: API calls go through Vite proxy (empty base URL).
 * In production:  Set VITE_API_URL to the backend URL (e.g., https://backend.azurewebsites.net).
 * 
 * Usage: import { API_BASE_URL } from '../config';
 *        fetch(`${API_BASE_URL}/api/configuration/set`, ...)
 */
export const API_BASE_URL = import.meta.env.VITE_API_URL || '';
