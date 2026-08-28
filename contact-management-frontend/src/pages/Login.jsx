import { useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../api/axiosConfig";

function Login() {

  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleLogin = async (e) => {

    e.preventDefault();

    setError("");
    setLoading(true);

    try {

      const response = await API.post("/auth/login", {
        email,
        password,
      });

      // Save JWT token
      localStorage.setItem("token", response.data.token);

      // Tell App.jsx authentication changed
      window.dispatchEvent(new Event("authChange"));

      // Go to Home after successful login
      navigate("/home");

    } catch (error) {

      console.error("Login error:", error);

      setError(
        error.response?.data?.message ||
        error.response?.data ||
        "Invalid email or password."
      );

    } finally {

      setLoading(false);

    }
  };

  return (
  <div className="auth-page">

    <div className="auth-card">

      <div className="card-body">

        <h2 className="auth-title text-center mb-2">
          Welcome Back
        </h2>

        <p className="auth-subtitle text-center mb-4">
          Sign in to your account to continue
        </p>

        {error && (
          <div className="alert alert-danger">
            {error}
          </div>
        )}

        <form onSubmit={handleLogin}>

          <div className="mb-3">
            <label className="form-label">
              Email
            </label>

            <input
              type="email"
              className="form-control"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label">
              Password
            </label>

            <input
              type="password"
              className="form-control"
              placeholder="Enter your password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <button
            type="submit"
            className="btn btn-app-primary w-100"
            disabled={loading}
          >
            {loading ? (
              <>
                <span
                  className="spinner-border spinner-border-sm me-2"
                  role="status"
                ></span>
                Logging in...
              </>
            ) : (
              "Login"
            )}
          </button>

        </form>

        <div className="text-center mt-3">
          <span>
            Don't have an account?{" "}
          </span>

          <button
            type="button"
            className="btn btn-link p-0"
            onClick={() => navigate("/register")}
          >
            Register
          </button>
        </div>

      </div>

    </div>

  </div>
);
}

export default Login;