import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
});

// Add JWT token to every request
API.interceptors.request.use(
  (config) => {

    const token = localStorage.getItem("token");

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Handle expired/invalid token
API.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {

    if (error.response?.status === 401) {

      localStorage.removeItem("token");

      window.dispatchEvent(
        new Event("authChange")
      );

      window.location.href = "/login";
    }

    return Promise.reject(error);
  }
);

export default API;