import React, { useState, useEffect } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import "../stylees.css";
import "bootstrap/dist/css/bootstrap.min.css"; // Import Bootstrap CSS
import {
  fetchSubjectAbstract,
  fetchImage,
  fetchSubjectComment,
} from "../services/api"; // Import the service
import CarouselComponent from "../components/CarouselComponent";

const Location = () => {
  const [abstract, setAbstract] = useState("");
  const [abstractParts, setAbstractParts] = useState([]);
  const [comment, setComment] = useState("");
  const [error, setError] = useState("");
  const [images, setImages] = useState([]);
  const location = useLocation();
  const { message, coordinates } = location.state || {};
  const navigate = useNavigate();

  const subjectResource = message;
  const [latitude, longitude] = coordinates.replace(/[<>]/g, "").split(", ");


  const splitAbstract = (abstract) => {
    const sentences = abstract.split(/(?<=\.)\s+/); // Split by sentences
    const half = Math.ceil(sentences.length / 2); // Find the middle point
    const firstHalf = sentences.slice(0, half).join(' ');
    const secondHalf = sentences.slice(half).join(' ');
    return [firstHalf, secondHalf];
  };

  useEffect(() => {
    const fetchAbstract = async () => {
      try {
        const result = await fetchSubjectAbstract(subjectResource);
        const cleanAbstract = result
          .replace(/^Abstract:\s*/, "")
          .replace(/@[\w-]+$/, "")
          .trim();
        const [firstHalf, secondHalf] = splitAbstract(cleanAbstract);
        setAbstractParts([firstHalf, secondHalf]);
      } catch (error) {
        setError("Failed to fetch subject abstract");
      }
    };

    const fetchComment = async () => {
      try {
        const result = await fetchSubjectComment(subjectResource);

        setComment(result);
      } catch (error) {
        setError("Failed to fetch subject abstract");
      }
    };

    const fetchImageForResource = async () => {
      try {
        const imageUrl = await fetchImage(subjectResource);
        setImages(imageUrl);
      } catch (error) {
        setError("Failed to fetch image");
      }
    };

    fetchAbstract();
    fetchComment();
    fetchImageForResource();
  }, [subjectResource]);

  const navigateToPage = (page) => {
    navigate(`/${page}`, { state: { message, coordinates } });
  };

  return (
    <div>
      <header
        className="hero-image"
        style={{ backgroundColor: "transparent", height: "auto" }}
      >
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark justify-content-center">
          <div
            className="container px-5 justify-content-center"
            style={{ width: "100%", height: "100px" }}
          >
            <div className="navbar-brand" style={{ width: "100px" }}>
              <a href="#!">Location Search API</a>
            </div>
            <button
              className="navbar-toggler"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span className="navbar-toggler-icon"></span>
            </button>
            <div
              className="collapse navbar-collapse justify-content-center"
              id="navbarSupportedContent"
            >
              <div
                style={{
                  position: "absolute",
                  top: "50%",
                  left: "64%",
                  transform: "translate(-50%, -50%)",
                }}
              >
                <div className="btn-group">
                  <button
                    className="button"
                    style={{
                      paddingRight: "40px",
                      width: "100%",
                      fontSize: "large",
                      whiteSpace: "nowrap",
                    }}
                  >
                    Login
                  </button>
                  <button
                    className="button"
                    style={{
                      paddingRight: "40px",
                      width: "100%",
                      fontSize: "large",
                      whiteSpace: "nowrap",
                    }}
                  >
                    Create Account
                  </button>
                </div>
              </div>
            </div>
          </div>
        </nav>

        <div
          className="d-flex"
          style={{
            position: "absolute",
            top: "12%",
            left: "50%",
            transform: "translate(-50%, -50%)",
          }}
        >
          <ul
            className="navbar-nav mr-auto mb-2 mb-lg-0"
            style={{ fontSize: "26px", flexDirection: "row" }}
          >
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <Link
                className="nav-link"
                style={{ color: "white" }}
                to="/about-us"
              >
                About Us
              </Link>
            </li>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <Link
                className="nav-link"
                style={{ color: "white" }}
                to="/tips-and-guidance"
              >
                Tips and Guidance
              </Link>
            </li>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <Link
                className="nav-link"
                style={{ color: "white" }}
                to="/city-maps"
              >
                City Maps
              </Link>
            </li>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <Link
                className="nav-link"
                style={{ color: "white" }}
                to="/events"
              >
                Events
              </Link>
            </li>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <Link
                className="nav-link"
                style={{ color: "white" }}
                to="/city-guides"
              >
                City Guides
              </Link>
            </li>
          </ul>
        </div>

        <div className="container px-5" style={{ width: "auto" }}>
          <div className="row gx-5 justify-content-center">
            <div className="col-lg-6" style={{ width: "auto" }}>
              <div className="text-center my-5">
                <h1
                  className="display-5 fw-bolder text-white mb-2"
                  style={{ marginTop: "220px" }}
                >
                  {subjectResource}
                </h1>
                <p
                  class="text"
                  style={{
                    color: "white",
                    paddingBottom: "5px",
                    fontSize: "20px",
                  }}
                >
                  {abstractParts[0]}
                </p>
              </div>
            </div>
          </div>
          <section>
            <div class="row">
              <div class="col-md-6 gx-5 mb-4">
                <div
                  class="bg-image hover-overlay shadow-2-strong"
                  data-mdb-ripple-init
                  data-mdb-ripple-color="light"
                >
                  <img
                    src="https://www.worldofcruising.co.uk/uploads/images/_1200x630_crop_center-center_82_none/vienna-city-1_2020-10-14-093122.jpg?mtime=1605881658"
                    className="img-fluid"
                    style={{
                      borderRadius: "8px",
                      height: "350px",
                    }}
                  />
                  <a href="#!">
                    <div
                      class="mask"
                      style={{ backgroundColor: "rgba(251, 251, 251, 0.15)" }}
                    ></div>
                  </a>
                </div>
              </div>

              <div class="col-md-6 gx-5 mb-4">
                <h4
                  style={{
                    color: "white",
                    fontSize: "36px",
                    paddingBottom: "20px",
                  }}
                >
                  <strong>Sights</strong>
                </h4>
                <p
                  class="text"
                  style={{
                    color: "white",
                    paddingBottom: "5px",
                    fontSize: "20px",
                  }}
                >
                  Find the most notable sights around you. Whether it's natural
                  wonders or architectural marvels, get all the information you
                  need to make the most of your exploration.
                </p>
                <button
                  className="btn btn-outline-light btn-lg px-4"
                  onClick={() => navigateToPage("sights")}
                >
                  Discover Sights
                  <i className="bi bi-arrow-right"></i>
                </button>
              </div>
            </div>
          </section>

          <hr class="my-5" />

          <section
            class="text-center"
            style={{
              display: "grid",
              placeItems: "center",
              width: "115%",
              margin: "0 auto",
            }}
          >
            <h4 class="mb-5" style={{ color: "white", fontSize: "36px" }}>
              <strong>Explore What's Around You</strong>
            </h4>

            <section class="py-5" id="features">
              <div className="row gx-5" style={{ width: "auto" }}>
                <div
                  className="col-lg-6 col-xl-4"
                  style={{ width: "485px", paddingLeft: "30px" }}
                >
                  <h2
                    className="h4 fw-bolder"
                    style={{ color: "white", fontSize: "26px" }}
                  >
                    Restaurants, Cafes
                  </h2>
                  <p style={{ justifyContent: "center", color: "white" }}>
                    Discover the best dining spots in your area. Whether you're
                    looking for a cozy cafe to relax in or a top-rated
                    restaurant for a special meal, find all the details you need
                    right here.
                  </p>
                  <div
                    className="card mb-5 mb-xl-0"
                    style={{
                      height: "500px",
                      backgroundColor: "transparent",
                      borderRadius: "8px",
                    }}
                  >
                    <div
                      className="hero2-image"
                      style={{ borderRadius: "8px" }}
                    >
                      <div className="card-body p-5">
                        <div className="mb-3"></div>
                        <div className="d-grid" style={{ paddingTop: "350px" }}>
                          <button
                            className="btn btn-outline-light btn-lg px-4"
                            onClick={() => navigateToPage("restaurants")}
                          >
                            Show Nearby
                            <i className="bi bi-arrow-right"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="col-lg-6 col-xl-4" style={{ width: "485px" }}>
                  <h2
                    className="h4 fw-bolder"
                    style={{ color: "white", fontSize: "26px" }}
                  >
                    Bars, Clubs, Pubs
                  </h2>
                  <p style={{ justifyContent: "center", color: "white" }}>
                    Explore the nightlife near you. From trendy bars with the
                    latest cocktails and live music to lively clubs where you
                    can dance the night away, and pubs offering a laid-back
                    atmosphere and a wide selection of beers.
                  </p>
                  <div
                    className="card mb-5 mb-xl-0"
                    style={{ height: "500px", backgroundColor: "transparent" }}
                  >
                    <div
                      className="hero4-image"
                      style={{ borderRadius: "8px" }}
                    >
                      <div className="card-body p-5">
                        <div className="mb-3"></div>
                        <div className="d-grid" style={{ paddingTop: "350px" }}>
                          <button
                            className="btn btn-outline-light btn-lg px-4"
                            onClick={() => navigateToPage("bars")}
                          >
                            Show Nearby
                            <i className="bi bi-arrow-right"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <div className="col-lg-6 col-xl-4" style={{ width: "485px" }}>
                  <h2
                    className="h4 fw-bolder"
                    style={{ color: "white", fontSize: "26px" }}
                  >
                    Tourist Attractions
                  </h2>
                  <p style={{ justifyContent: "center", color: "white" }}>
                    Uncover must-see tourist attractions in your vicinity. From
                    historic landmarks to popular destinations, find out what
                    makes your location special and plan your visit.
                  </p>
                  <div
                    className="card mb-5 mb-xl-0"
                    style={{ height: "500px", backgroundColor: "transparent" }}
                  >
                    <div
                      className="hero5-image"
                      style={{ borderRadius: "8px" }}
                    >
                      <div className="card-body p-5">
                        <div className="mb-3"></div>
                        <div className="d-grid" style={{ paddingTop: "350px" }}>
                          <button
                            className="btn btn-outline-light btn-lg px-4"
                            onClick={() => navigateToPage("attractions")}
                          >
                            Show Nearby
                            <i className="bi bi-arrow-right"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </section>
          </section>
        </div>
      </header>
      <CarouselComponent images={images} abstract={abstractParts[1]} error={error} />

      <div className="footer-dark">
        <footer>
          <div className="container">
            <div className="row justify-content-center">
              <div className="col-sm-4 col-md-3 item">
                <h3>Services</h3>
                <ul>
                  <li>
                    <a href="#">Shoppen bei Iceberg Lounge</a>
                  </li>
                  <li>
                    <a href="#">Iceberg Lounge Events</a>
                  </li>
                  <li>
                    <a href="#">Aktionen & Rabatte</a>
                  </li>
                </ul>
              </div>
              <div className="col-sm-4 col-md-3 item">
                <h3>Häufig gestellte Fragen</h3>
                <ul>
                  <li>
                    <a href="#">FAQs</a>
                  </li>
                  <li>
                    <a href="#">Versand & Lieferung</a>
                  </li>
                  <li>
                    <a href="#">Rückgabe & Umtausch</a>
                  </li>
                  <li>
                    <a href="#">Bezahlmöglichkeiten</a>
                  </li>
                </ul>
              </div>
              <div className="col-sm-4 col-md-3 item">
                <h3>Über Icerberg Lounge</h3>
                <ul>
                  <li>
                    <a href="#">Unternehmen</a>
                  </li>
                  <li>
                    <a href="#">Jobs</a>
                  </li>
                  <li>
                    <a href="#">Newsletter</a>
                  </li>
                </ul>
              </div>
              <div className="col-lg-3 item social">
                <a href="#">
                  <i className="icon ion-social-facebook"></i>
                </a>
                <a href="#">
                  <i className="icon ion-social-twitter"></i>
                </a>
                <a href="#">
                  <i class="icon ion-social-snapchat"></i>
                </a>
                <a href="#">
                  <i className="icon ion-social-instagram"></i>
                </a>
                <p className="copyright">Iceberg Lounge © 2023</p>
              </div>
            </div>
          </div>
        </footer>
      </div>
    </div>
  );
};

export default Location;
