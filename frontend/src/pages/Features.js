import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Features = () => {
  const navigate = useNavigate();
  const [file, setFile] = useState(null);
  const [coordinates, setCoordinates] = useState(null);
  const [message, setMessage] = useState("");

  const navigateToLogin = () => {
    navigate("/login");
  };

  const navigateToSignup = () => {
    navigate("/signup");
  };

  const onFileChange = (e) => {
    setFile(e.target.files[0]);
    readURL(e.target);
  };

  const onFileUpload = async () => {
    if (!file) {
      setMessage("Please select a file first");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      /*
 const uploadResponse = await axios.post(
   "https://eis.dke.univie.ac.at/locationSearch/image",
   formData,
   {
     headers: {
       "Content-Type": "multipart/form-data",
     },
   }
 );
 const location = uploadResponse.data;
 setMessage(`Success: ${location}`);

 */
      const uploadResponse = await axios.post(
        "https://eis.dke.univie.ac.at/locationSearch/image",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      const location = uploadResponse.data;
      setMessage(`${location}`);


      /*
     const uploadResponse = await axios.post(
       "https://eis.dke.univie.ac.at/locationSearch/coordinates",
       formData,
       {
         headers: {
           "Content-Type": "multipart/form-data",
         },
       }
     );
     const location = uploadResponse.data;
     setMessage(`Success: ${location}`);
     */
      const coordinatesResponse = await axios.get(
        "https://eis.dke.univie.ac.at/locationSearch/coordinates",
        {
          params: { location },
        }
      );
      setCoordinates(coordinatesResponse.data);
    } catch (error) {
      console.error("Error:", error);
      setMessage(`Error: ${error.message}`);
    }
  };

  const readURL = (input) => {
    if (input.files && input.files[0]) {
      const reader = new FileReader();
      reader.onload = (e) => {
        document.querySelector(".image-upload-wrap").style.display = "none";
        document.querySelector(".file-upload-image").src = e.target.result;
        document.querySelector(".file-upload-content").style.display = "block";
        document.querySelector(".image-title").innerHTML = input.files[0].name;
      };
      reader.readAsDataURL(input.files[0]);
    } else {
      removeUpload();
    }
  };

  const removeUpload = () => {
    const fileUploadInput = document.querySelector(".file-upload-input");
    fileUploadInput.value = "";
    document.querySelector(".file-upload-content").style.display = "none";
    document.querySelector(".image-upload-wrap").style.display = "block";
  };

  const navigateToNearbyAttractions = () => {
    if (coordinates) {
      navigate('/categories', { state: { coordinates } });
    } else {
      setMessage("Please upload an image to get coordinates first.");
    }
  };

  const navigateToLocationSpecifics = () => {
    if (coordinates) {
      navigate('/location', { state: { message, coordinates } });
    } else {
      setMessage("Please upload an image to get coordinates first.");
    }
  };

  return (
    <div>
      <header className="hero-image">
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark justify-content-center">
          <div
            className="container px-5 justify-content-center"
            style={{ width: "100%", height: "100px" }}
          >
            <div className="navbar-brand" style={{ width: "100px" }}>
              <a href="https://eis.dke.univie.ac.at/locationSearch">Location Search API</a>
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
                    onClick={navigateToLogin}
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
                    onClick={navigateToSignup}
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
              <a
                className="nav-link"
                style={{ color: "white" }}
                aria-current="page"
                href="/products"
              >
                About Us
              </a>
            </li>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <a
                className="nav-link"
                style={{ color: "white" }}
                href="/products"
              >
                Tips and Guidance
              </a>
            </li>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <a
                className="nav-link"
                style={{ color: "white" }}
                href="/products"
              >
                City Maps
              </a>
            </li>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <a
                className="nav-link"
                style={{ color: "white" }}
                href="/products"
              >
                Events
              </a>
            </li>
            <li className="nav-item" style={{ paddingRight: "20px" }}>
              <a
                className="nav-link"
                style={{ color: "white" }}
                href="/products"
              >
                City Guides
              </a>
            </li>
          </ul>
        </div>

        <div className="text-center my-5">
          <h1
            className="display-5 fw-bolder text-white mb-2"
            style={{ marginTop: "220px" }}
          >
            CityScape
          </h1>
          <div>
            <p className="lead text-white mb-4">
              Explore cities effortlessly with personalized recommendation
            </p>
          </div>

          <div
            className="container px-5"
            style={{ paddingLeft: "0rem", paddingRight: "6rem" }}
          >
            <div className="row gx-5 justify-content-center">
              <div className="col-lg-6">
                <div className="file-upload">
                  <button
                    className="btn btn-outline-light btn-lg px-4"
                    type="button"
                    onClick={() =>
                      document.querySelector(".file-upload-input").click()
                    }
                  >
                    Upload Image
                  </button>

                  <div className="image-upload-wrap">
                    <input
                      className="file-upload-input"
                      type="file"
                      onChange={onFileChange}
                      accept="image/*"
                    />
                    <div className="drag-text">
                      <h3>Drag and drop your file here!</h3>
                    </div>
                  </div>
                  <div className="file-upload-content">
                    <img
                      className="file-upload-image"
                      src="#"
                      alt="your image"
                    />
                    <div className="image-title-wrap">
                      <button
                        type="button"
                        onClick={removeUpload}
                        className="btn btn-outline-light btn-lg px-4"
                      >
                        Remove{" "}
                        <span className="image-title">Uploaded Image</span>
                      </button>
                    </div>
                  </div>
                </div>
                <button onClick={onFileUpload} className="btn btn-primary mt-4">
                  Upload
                </button>
                <p>{message}</p>
                {coordinates && (
                  <div>
                    <h3>Coordinates:</h3>
                    <p>{coordinates}</p>
                    <button onClick={navigateToLocationSpecifics} className="btn btn-secondary mt-4">
                      Go to Nearby Attractions
                    </button>
                  </div>
                )}
                {coordinates && console.log(coordinates)}
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
                  <i className="icon ion-social-snapchat"></i>
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

export default Features;
