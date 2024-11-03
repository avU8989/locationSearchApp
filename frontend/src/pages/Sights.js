import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import { fetchImageEng, fetchSubjectAbstract } from "../services/api";
import CarouselComponent from "../components/CarouselComponentSights";
const Sights = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { coordinates } = location.state || {};
  const [latitude, setLatitude] = useState(null);
  const [longitude, setLongitude] = useState(null);
  const [sights, setSights] = useState([]);
  const [images, setImages] = useState({});
  const [error, setError] = useState(null);

  useEffect(() => {
    if (coordinates) {
      const [lat, long] = coordinates.replace(/[<>]/g, "").split(", ");
      setLatitude(lat);
      setLongitude(long);

      axios.get(`https://eis.dke.univie.ac.at/locationSearch/api/sparql/nearby-sights`, { params: { latitude: lat, longitude: long } })
        .then(async (response) => {
          const sightsData = response.data;
          setSights(sightsData);

          const newImages = {};
          for (const sight of sightsData) {
            try {
              const image = await fetchImageEng(sight.placeLabel);

              newImages[sight.placeLabel] = {
                image,
              
              };

              console.debug(newImages);
            } catch (error) {
              console.error(`Error fetching data for ${sight.placeLabel}:`, error);
              setError(`Error fetching data for ${sight.placeLabel}`);
            }
          }
          setImages(newImages);
        })
        .catch((error) => {
          console.error("Error fetching sights:", error);
          setError("Error fetching sights");
        });
    }
  }, [coordinates]);

  const imageList = sights.map(sight => images[sight.placeLabel]?.image).filter(image => image);
  console.debug(imageList);

  const abstractList = sights.map(sight => images[sight.placeLabel]?.abstract).filter(abstract => abstract);
  console.debug(abstractList);

  return (
    <div>
      <header className="hero-image">
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark justify-content-center">
          <div className="container px-5 justify-content-center" style={{ width: "100%", height: "100px" }}>
            <div className="navbar-brand" style={{ width: "100px" }}>
              <a href="https://eis.dke.univie.ac.at/locationSearch/">Location Search API</a>
            </div>
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse justify-content-center" id="navbarSupportedContent">
              <div style={{ position: "absolute", top: "50%", left: "64%", transform: "translate(-50%, -50%)" }}>
                <div className="btn-group">
                  <button className="button" style={{ paddingRight: "40px", width: "100%", fontSize: "large", whiteSpace: "nowrap" }} onClick={() => navigate("/login")}>
                    Login
                  </button>
                  <button className="button" style={{ paddingRight: "40px", width: "100%", fontSize: "large", whiteSpace: "nowrap" }} onClick={() => navigate("/signup")}>
                    Create Account
                  </button>
                </div>
              </div>
            </div>
          </div>
        </nav>

        <div className="d-flex" style={{ position: "absolute", top: "12%", left: "50%", transform: "translate(-50%, -50%)" }}>
          <ul className="navbar-nav mr-auto mb-2 mb-lg-0" style={{ fontSize: "26px", flexDirection: "row" }}>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <a className="nav-link" style={{ color: "white" }} aria-current="page" href="/products">About Us</a>
            </li>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <a className="nav-link" style={{ color: "white" }} href="/products">Tips and Guidance</a>
            </li>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <a className="nav-link" style={{ color: "white" }} href="/products">City Maps</a>
            </li>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <a className="nav-link" style={{ color: "white" }} href="/products">Events</a>
            </li>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <a className="nav-link" style={{ color: "white" }} href="/products">City Guides</a>
            </li>
          </ul>
        </div>

        <div className="text-center my-5">
          <div className="row gx-5 justify-content-center">
            <div className="col-lg-8 text-white">
              <h1>Nearby Sights</h1>
              {imageList.length > 0 ? (
                <CarouselComponent sights = {sights} images={imageList} error={error} />
              ) : (
                <p>Loading sights....</p>
              )}
            </div>
          </div>
        </div>

        <div className="text-center my-5">
          <button className="btn btn-primary" onClick={() => navigate("/location", { state: {coordinates } })}>
            Go Back to Location
          </button>
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
                <a href="#"><i className="icon ion-social-snapchat"></i></a>
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

export default Sights;
