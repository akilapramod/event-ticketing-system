/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#001A6E',
          light: '#074799',
        },
        accent: {
          DEFAULT: '#009990',
          light: '#E1FFBB',
        },
      },
    },
  },
  plugins: [],
}
