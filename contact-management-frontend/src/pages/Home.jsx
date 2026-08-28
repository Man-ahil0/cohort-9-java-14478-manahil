import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

function Home() {
  const navigate = useNavigate();

  const images = [
    "https://images.unsplash.com/photo-1556911220-bff31c812dba?auto=format&fit=crop&w=2000&q=85",
    "https://images.unsplash.com/photo-1551218808-94e220e084d2?auto=format&fit=crop&w=2000&q=85",
    "https://images.unsplash.com/photo-1556910103-1c02745aae4d?auto=format&fit=crop&w=2000&q=85",
    "https://images.unsplash.com/photo-1506368249639-73a05d6f6488?auto=format&fit=crop&w=2000&q=85",
  ];

  const [currentImage, setCurrentImage] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentImage((prev) => (prev + 1) % images.length);
    }, 5000);

    return () => clearInterval(interval);
  }, []);

  return (
    <div className="home-page">

      {/* Hero Section */}

      <section className="home-hero">

        {images.map((image, index) => (
          <div
            key={image}
            className={`hero-background ${
              index === currentImage ? "active" : ""
            }`}
            style={{
              backgroundImage: `url(${image})`,
            }}
          />
        ))}

        <div className="hero-overlay"></div>

        <div className="hero-content">

          <p className="hero-small-title">
            CONTACT MANAGEMENT SYSTEM
          </p>

          <h1>
            Welcome to Contact Manager
          </h1>

          <p className="hero-description">
            Keep your contacts organized, accessible,
            and easy to manage — all in one place.
          </p>

          <button
            className="hero-contact-button"
            onClick={() => navigate("/contacts")}
          >
            <span>Go to Contacts</span>
            <span className="hero-arrow">↓</span>
          </button>

        </div>

      </section>


      {/* Contact Information */}

      <section className="home-contact-section">

        <div className="container">

          <div className="text-center mb-4">

            <p className="contact-small-title">
              GET IN TOUCH
            </p>

            <h2>
              Contact Information
            </h2>

            <p className="contact-description">
              Have a question? Feel free to reach out to us.
            </p>

          </div>


          <div className="row justify-content-center g-4">

            <div className="col-md-4">

              <div className="contact-info-item">

                <div className="contact-icon">
                  ◎
                </div>

                <h5>Instagram</h5>

                <p>
                  manahil10_
                </p>

              </div>

            </div>


            <div className="col-md-4">

              <div className="contact-info-item">

                <div className="contact-icon">
                  @
                </div>

                <h5>Email</h5>

                <p>
                  waheedmanahil490@gmail.com
                </p>

              </div>

            </div>


            <div className="col-md-4">

              <div className="contact-info-item">

                <div className="contact-icon">
                  ☎
                </div>

                <h5>Phone</h5>

                <p>
                  +92 333 5533751
                </p>

              </div>

            </div>

          </div>

        </div>

      </section>


      {/* Footer */}

      <footer className="home-footer">

        <p>
          © 2026 Contact Manager. All rights reserved.
        </p>

      </footer>

    </div>
  );
}

export default Home;