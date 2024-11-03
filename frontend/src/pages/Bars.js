import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import 'mdb-react-ui-kit/dist/css/mdb.min.css';
import { MDBContainer, MDBRow, MDBCol, MDBCard, MDBCardBody, MDBCardTitle, MDBCardText, MDBCardImage } from 'mdb-react-ui-kit';
import defaultImage from '../assets/images/bars.jpg';
import backgroundImage from '../assets/images/test3.jpg';

const Bars = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const { message, coordinates } = location.state || {};
    const [latitude, setLatitude] = useState(null);
    const [longitude, setLongitude] = useState(null);
    const [bars, setBars] = useState([]);

    useEffect(() => {
        if (coordinates) {
            const [lat, long] = coordinates.replace(/[<>]/g, '').split(', ');
            setLatitude(lat);
            setLongitude(long);

            // Fetch nearby bars with detailed information
            axios.get(`/locationSearch/api/sparql/nearby-bars`, { params: { latitude: lat, longitude: long } })
                .then(async response => {
                    const barsWithPhotos = await Promise.all(response.data.map(async bar => {
                        const photoUrl = await fetchBarPhoto(bar.placeId);
                        return { ...bar, photoUrl };
                    }));
                    setBars(barsWithPhotos);
                })
                .catch(error => console.error('Error fetching bars:', error));
        }
    }, [coordinates]);

    const fetchBarPhoto = async (placeId) => {
        try {
            const response = await axios.get(`https://maps.googleapis.com/maps/api/place/details/json`, {
                params: {
                    place_id: placeId,
                    key: 'YOUR_GOOGLE_MAPS_API_KEY'
                }
            });
            const photoRef = response.data.result.photos ? response.data.result.photos[0].photo_reference : null;
            return photoRef
                ? `https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${photoRef}&key=YOUR_GOOGLE_MAPS_API_KEY`
                : defaultImage;
        } catch (error) {
            console.error('Error fetching bar photo:', error);
            return defaultImage;
        }
    };

    const renderBar = (bar, index) => (
        <MDBCol key={index} md="3" className="mb-4">
            <MDBCard>
                <MDBCardImage src={bar.photoUrl || defaultImage} position="top" alt={bar.placeLabel} style={{ height: '145px', objectFit: 'cover' }} />
                <MDBCardBody>
                    <MDBCardTitle>{bar.placeLabel}</MDBCardTitle>
                    <MDBCardText>{bar.description}</MDBCardText>
                    <MDBCardText><strong>Distance:</strong> {bar.distance}m</MDBCardText>
                    <a href={`https://www.google.com/maps/search/?api=1&query=${bar.placeLabel}`} target="_blank" rel="noopener noreferrer" className="category-link">
                        View on Google Maps
                    </a>
                </MDBCardBody>
            </MDBCard>
        </MDBCol>
    );

    const renderList = (title, items) => (
        <MDBContainer>
            <h3 className="category-title">{title}</h3>
            <MDBRow>
                {items.slice(0, 4).map((item, index) => renderBar(item, index))}
            </MDBRow>
            <MDBRow>
                {items.slice(4, 8).map((item, index) => renderBar(item, index))}
            </MDBRow>
            <MDBRow className="justify-content-center">
                {items.slice(8, 10).map((item, index) => renderBar(item, index))}
            </MDBRow>
        </MDBContainer>
    );

    return (
        <div style={{ backgroundImage: `url(${backgroundImage})`, backgroundSize: 'cover', backgroundPosition: 'center', minHeight: '100vh' }}>
            <header className="hero-image">
                <nav className="navbar navbar-expand-lg navbar-dark bg-dark justify-content-center">
                    <div className="container px-5 justify-content-center" style={{ width: "100%", height: "100px" }}>
                        <div className="navbar-brand" style={{ width: "100px" }}>
                            <a href="https://eis.dke.univie.ac.at/locationSearch/">Location Search API</a>
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
                        <div className="collapse navbar-collapse justify-content-center" id="navbarSupportedContent">
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
                                        onClick={() => navigate("/login")}
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
                                        onClick={() => navigate("/signup")}
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
                    <div className="row gx-5 justify-content-center">
                        <div className="col-lg-10 text-white">
                            {renderList('Bars, Clubs, Pubs', bars)}
                        </div>
                    </div>
                </div>
                <div className="text-center my-5">
                    <button
                        className="btn btn-primary"
                        onClick={() => navigate('/location', { state: { message, coordinates } })}
                        style={{ position: "fixed", bottom: "20px", right: "20px" }}
                    >
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

export default Bars;
