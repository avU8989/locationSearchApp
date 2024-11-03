import React from 'react';
import { Link } from 'react-router-dom';
import '../stylees.css';

const Home = () => {
  return (
    <div>
      <header className="hero-image">
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark justify-content-center">
          <div className="container px-5 justify-content-center" style={{ width: "100%", height: "100px" }}>
            <div className="navbar-brand" style={{ width: "100px" }}>
              <a href="https://eis.dke.univie.ac.at/locationSearch/">Location Search API</a>
            </div>
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse justify-content-center" id="navbarSupportedContent">
              <div style={{ position: "absolute", top: "50%", left: "64%", transform: "translate(-50%, -50%)" }}>
                <div className="btn-group">
                  <button className="button" style={{ paddingRight: "40px", width: "100%", fontSize: "large", whiteSpace: "nowrap" }}>Login</button>
                  <button className="button" style={{ paddingRight: "40px", width: "100%", fontSize: "large", whiteSpace: "nowrap" }}>Create Account</button>
                </div>
              </div>
            </div>
          </div>
        </nav>

        <div className="d-flex" style={{ position: "absolute", top: "12%", left: "50%", transform: "translate(-50%, -50%)" }}>
          <ul className="navbar-nav mr-auto mb-2 mb-lg-0" style={{ fontSize: "26px", flexDirection: "row" }}>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <Link className="nav-link" style={{ color: "white" }} to="/about-us">About Us</Link>
            </li>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <Link className="nav-link" style={{ color: "white" }} to="/tips-and-guidance">Tips and Guidance</Link>
            </li>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <Link className="nav-link" style={{ color: "white" }} to="/city-maps">City Maps</Link>
            </li>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <Link className="nav-link" style={{ color: "white" }} to="/events">Events</Link>
            </li>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <Link className="nav-link" style={{ color: "white" }} to="/city-guides">City Guides</Link>
            </li>
          </ul>
        </div>

        <div className="container px-5">
          <div className="row gx-5 justify-content-center">
            <div className="col-lg-6">
              <div className="text-center my-5">
                <h1 className="display-5 fw-bolder text-white mb-2" style={{ marginTop: "220px" }}>CityScape</h1>
                <div>
                  <p className="lead text-white mb-4">Explore cities effortlessly with personalized recommendations</p>
                </div>

                <div className="d-grid gap-3 d-sm-flex justify-content-sm-center">
                  <Link to="/features" className="btn btn-outline-light btn-lg px-4">Start Your Urban Adventure</Link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </header>

      <div className="footer-dark">
        <footer>
          <div className="container">
            <div className="row justify-content-center">
              <div className="col-sm-4 col-md-3 item">
                <h3>Services</h3>
                <ul>
                  <li><a href="#">Shoppen bei Iceberg Lounge</a></li>
                  <li><a href="#">Iceberg Lounge Events</a></li>
                  <li><a href="#">Aktionen & Rabatte</a></li>
                </ul>
              </div>
              <div className="col-sm-4 col-md-3 item">
                <h3>Häufig gestellte Fragen</h3>
                <ul>
                  <li><a href="#">FAQs</a></li>
                  <li><a href="#">Versand & Lieferung</a></li>
                  <li><a href="#">Rückgabe & Umtausch</a></li>
                  <li><a href="#">Bezahlmöglichkeiten</a></li>
                </ul>
              </div>
              <div className="col-sm-4 col-md-3 item">
                <h3>Über Icerberg Lounge</h3>
                <ul>
                  <li><a href="#">Unternehmen</a></li>
                  <li><a href="#">Jobs</a></li>
                  <li><a href="#">Newsletter</a></li>
                </ul>
              </div>
              <div className="col-lg-3 item social">
                <a href="#"><i className="icon ion-social-facebook"></i></a>
                <a href="#"><i className="icon ion-social-twitter"></i></a>
                <a href="#"><i class="icon ion-social-snapchat"></i></a>
                <a href="#"><i className="icon ion-social-instagram"></i></a>
                <p className="copyright">Iceberg Lounge © 2023</p>
              </div>
            </div>
          </div>
        </footer>
      </div>
    </div>
  );
};

export default Home;
