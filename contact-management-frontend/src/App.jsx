import { useEffect, useState } from "react";
import {
  BrowserRouter,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";

import Navbar from "./components/Navbar";

import Home from "./pages/Home";
import Contacts from "./pages/Contacts";
import Login from "./pages/Login";
import Register from "./pages/Register";
import ProtectedRoute from "./components/ProtectedRoute";
import Profile from "./pages/Profile";

function App() {

  const [isAuthenticated, setIsAuthenticated] = useState(
    !!localStorage.getItem("token")
  );

  useEffect(() => {

    const handleAuthChange = () => {
      setIsAuthenticated(!!localStorage.getItem("token"));
    };

    window.addEventListener("authChange", handleAuthChange);

    return () => {
      window.removeEventListener("authChange", handleAuthChange);
    };

  }, []);

  return (
    <BrowserRouter>

      {isAuthenticated && <Navbar />}

      <Routes>

        {/* Root */}
        <Route
          path="/"
          element={
            isAuthenticated
              ? <Navigate to="/home" replace />
              : <Navigate to="/login" replace />
          }
        />

        {/* Login */}
        <Route
          path="/login"
          element={
            isAuthenticated
              ? <Navigate to="/home" replace />
              : <Login />
          }
        />

        {/* Register */}
        <Route
          path="/register"
          element={
            isAuthenticated
              ? <Navigate to="/home" replace />
              : <Register />
          }
        />

        {/* Home */}
        <Route
          path="/home"
          element={
            <ProtectedRoute>
              <Home />
            </ProtectedRoute>
          }
        />

        {/* Contacts */}
        <Route
          path="/contacts"
          element={
            <ProtectedRoute>
              <Contacts />
            </ProtectedRoute>
          }
        />

        {/* Profile */}
        <Route
          path="/profile"
          element={
            <ProtectedRoute>
              <Profile />
            </ProtectedRoute>
          }
        />

        {/* Unknown URL */}
        <Route
          path="*"
          element={<Navigate to="/" replace />}
        />

      </Routes>

    </BrowserRouter>
  );
}

export default App;