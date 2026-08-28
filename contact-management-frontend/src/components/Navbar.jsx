import "bootstrap/dist/css/bootstrap.min.css";
import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import API from "../api/axiosConfig";

function Navbar() {
  const navigate = useNavigate();

  const [showLogoutModal, setShowLogoutModal] = useState(false);
  const [showSidebar, setShowSidebar] = useState(false);
  const [user, setUser] = useState(null);

  const isLoggedIn = !!localStorage.getItem("token");

  useEffect(() => {
    if (!isLoggedIn) {
      return;
    }

    fetchUser();

    window.addEventListener("authChange", fetchUser);
    window.addEventListener("profileUpdated", fetchUser);

    return () => {
      window.removeEventListener("authChange", fetchUser);
      window.removeEventListener("profileUpdated", fetchUser);
    };
  }, [isLoggedIn]);

  const fetchUser = async () => {
    try {
      const response = await API.get("/users/me");
      setUser(response.data);
    } catch (error) {
      console.error("Unable to load navbar user:", error);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");

    setShowLogoutModal(false);
    setShowSidebar(false);
    setUser(null);

    window.dispatchEvent(new Event("authChange"));

    navigate("/login");
  };

  if (!isLoggedIn) {
    return null;
  }

  const initials =
    `${user?.firstName?.charAt(0) || ""}${user?.lastName?.charAt(0) || ""}`
      .toUpperCase();

  return (
    <>
      {/* NAVBAR */}
      <nav className="navbar app-navbar">
        <div className="container">

          {/* LEFT SIDE */}
          <div className="d-flex align-items-center">

            {/* Profile */}
            <Link
              to="/profile"
              className="navbar-profile-link"
              title="My Profile"
            >
              {user?.profilePicture ? (
                <img
                  src={user.profilePicture}
                  alt="Profile"
                  className="navbar-profile-image"
                />
              ) : (
                <div className="navbar-profile-avatar">
                  {initials || "U"}
                </div>
              )}
            </Link>

            {/* Brand */}
            <Link
              className="navbar-brand ms-3"
              to="/home"
            >
              Contact Manager
            </Link>

          </div>

          {/* DESKTOP NAVIGATION */}
          <div className="desktop-navbar">
            <ul className="navbar-nav">

              <li className="nav-item">
                <Link
                  className="nav-link"
                  to="/home"
                >
                  Home
                </Link>
              </li>

              <li className="nav-item">
                <Link
                  className="nav-link"
                  to="/contacts"
                >
                  Contacts
                </Link>
              </li>

              <li className="nav-item">
                <button
                  className="nav-link btn btn-link"
                  onClick={() => setShowLogoutModal(true)}
                >
                  Logout
                </button>
              </li>

            </ul>
          </div>

          {/* MOBILE 3 LINES */}
          <button
            className="mobile-menu-button"
            type="button"
            onClick={() => setShowSidebar(!showSidebar)}
          >
            ☰
          </button>

        </div>
      </nav>

      {/* SIDEBAR */}
      {showSidebar && (
        <>
          <div
            className="sidebar-overlay"
            onClick={() => setShowSidebar(false)}
          ></div>

          <div className="mobile-sidebar">

            <div className="sidebar-header">
              <span>Menu</span>

              <button
                onClick={() => setShowSidebar(false)}
              >
                ×
              </button>
            </div>

            <Link
              to="/home"
              onClick={() => setShowSidebar(false)}
            >
              Home
            </Link>

            <Link
              to="/contacts"
              onClick={() => setShowSidebar(false)}
            >
              Contacts
            </Link>

            <Link
              to="/profile"
              onClick={() => setShowSidebar(false)}
            >
              My Profile
            </Link>

            <button
              className="sidebar-logout"
              onClick={() => {
                setShowSidebar(false);
                setShowLogoutModal(true);
              }}
            >
              Logout
            </button>

          </div>
        </>
      )}

      {/* LOGOUT MODAL */}
      {showLogoutModal && (
        <>
          <div className="logout-overlay"></div>

          <div className="logout-modal">

            <div className="logout-icon">
              ↪
            </div>

            <h4>Logout</h4>

            <p>
              Are you sure you want to logout?
            </p>

            <div className="logout-actions">

              <button
                type="button"
                className="logout-cancel-btn"
                onClick={() => setShowLogoutModal(false)}
              >
                Cancel
              </button>

              <button
                type="button"
                className="logout-confirm-btn"
                onClick={handleLogout}
              >
                Logout
              </button>

            </div>

          </div>
        </>
      )}
    </>
  );
}

export default Navbar;