import { useState } from "react";
import API from "../api/axiosConfig";

function ChangePasswordModal({ show, onClose }) {

  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const handleChangePassword = async () => {

    setMessage("");
    setError("");

    if (!currentPassword || !newPassword) {
      setError("Please fill in both password fields.");
      return;
    }

    if (newPassword.length < 6) {
      setError("New password must be at least 6 characters.");
      return;
    }

    try {

      setLoading(true);

      await API.put(
        "/users/change-password",
        {
          currentPassword,
          newPassword
        }
      );

      setMessage("Password changed successfully!");

      setCurrentPassword("");
      setNewPassword("");

    } catch (error) {

      console.error(error);

      setError(
        error.response?.data?.message ||
        error.response?.data ||
        "Failed to change password."
      );

    } finally {
      setLoading(false);
    }
  };

  const handleClose = () => {

    setCurrentPassword("");
    setNewPassword("");
    setMessage("");
    setError("");

    onClose();
  };

  if (!show) {
    return null;
  }

  return (
    <>
      <div
        className="modal fade show d-block"
        tabIndex="-1"
        role="dialog"
      >

        <div className="modal-dialog">

          <div className="modal-content shadow">

            <div className="modal-header bg-primary text-white">

              <h5 className="modal-title">
                Change Password
              </h5>

              <button
                type="button"
                className="btn-close btn-close-white"
                onClick={handleClose}
              ></button>

            </div>

            <div className="modal-body">

              {message && (
                <div className="alert alert-success">
                  {message}
                </div>
              )}

              {error && (
                <div className="alert alert-danger">
                  {error}
                </div>
              )}

              <div className="mb-3">

                <label className="form-label">
                  Current Password
                </label>

                <input
                  type="password"
                  className="form-control"
                  value={currentPassword}
                  onChange={(e) =>
                    setCurrentPassword(e.target.value)
                  }
                  disabled={loading}
                />

              </div>

              <div className="mb-3">

                <label className="form-label">
                  New Password
                </label>

                <input
                  type="password"
                  className="form-control"
                  value={newPassword}
                  onChange={(e) =>
                    setNewPassword(e.target.value)
                  }
                  disabled={loading}
                />

              </div>

            </div>

            <div className="modal-footer">

              <button
                className="btn btn-secondary"
                onClick={handleClose}
                disabled={loading}
              >
                Cancel
              </button>

              <button
                className="btn btn-primary"
                onClick={handleChangePassword}
                disabled={loading}
              >

                {loading ? (
                  <>
                    <span
                      className="spinner-border spinner-border-sm me-2"
                      role="status"
                    ></span>

                    Changing...
                  </>
                ) : (
                  "Change Password"
                )}

              </button>

            </div>

          </div>

        </div>

      </div>

      <div className="modal-backdrop fade show"></div>
    </>
  );
}

export default ChangePasswordModal;