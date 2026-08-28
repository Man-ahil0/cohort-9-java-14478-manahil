import { useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../api/axiosConfig";

function Register() {

  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    password: "",
  });

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleRegister = async (e) => {
    e.preventDefault();

    setError("");
    setSuccess("");
    setLoading(true);

    try {
      await API.post("/auth/register", formData);

      setSuccess("Registration successful! Redirecting to login...");

      setTimeout(() => {
        navigate("/login");
      }, 1500);

    } catch (error) {

      console.error("Registration error:", error);

      setError(
        error.response?.data?.message ||
        error.response?.data ||
        "Registration failed. Please try again."
      );

    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mt-5">

      <div className="row justify-content-center">
<div className="auth-page">

  <div className="auth-card" style={{ maxWidth: "600px" }}>

    <div className="card-body">

              <h2 className="auth-title text-center mb-2">
  Create Account
</h2>

<p className="auth-subtitle text-center mb-4">
  Register to start managing your contacts
</p>

              {error && (
                <div className="alert alert-danger">
                  {error}
                </div>
              )}

              {success && (
                <div className="alert alert-success">
                  {success}
                </div>
              )}

              <form onSubmit={handleRegister}>

                <div className="row">

                  {/* First Name */}
                  <div className="col-md-6 mb-3">

                    <label className="form-label">
                      First Name
                    </label>

                    <input
                      type="text"
                      name="firstName"
                      className="form-control"
                      value={formData.firstName}
                      onChange={handleChange}
                      required
                    />

                  </div>

                  {/* Last Name */}
                  <div className="col-md-6 mb-3">

                    <label className="form-label">
                      Last Name
                    </label>

                    <input
                      type="text"
                      name="lastName"
                      className="form-control"
                      value={formData.lastName}
                      onChange={handleChange}
                      required
                    />

                  </div>

                  {/* Email */}
                  <div className="col-md-6 mb-3">

                    <label className="form-label">
                      Email
                    </label>

                    <input
                      type="email"
                      name="email"
                      className="form-control"
                      value={formData.email}
                      onChange={handleChange}
                      required
                    />

                  </div>

                  {/* Phone */}
                  <div className="col-md-6 mb-3">

                    <label className="form-label">
                      Phone Number
                    </label>

                    <input
                      type="text"
                      name="phoneNumber"
                      className="form-control"
                      value={formData.phoneNumber}
                      onChange={handleChange}
                      required
                    />

                  </div>

                  {/* Password */}
                  <div className="col-12 mb-3">

                    <label className="form-label">
                      Password
                    </label>

                    <input
                      type="password"
                      name="password"
                      className="form-control"
                      value={formData.password}
                      onChange={handleChange}
                      required
                    />

                  </div>

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

                      Creating Account...
                    </>
                  ) : (
                    "Register"
                  )}

                </button>

              </form>

              <div className="text-center mt-3">

                <span>
                  Already have an account?{" "}
                </span>

                <button
                  type="button"
                  className="btn btn-link p-0"
                  onClick={() => navigate("/login")}
                >
                  Login
                </button>

              </div>

            </div>

          </div>

        </div>

      </div>

    </div>
   
  );
}

export default Register;