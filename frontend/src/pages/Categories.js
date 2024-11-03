import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

const Categories = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const { coordinates } = location.state || {};
    const [latitude, setLatitude] = useState(null);
    const [longitude, setLongitude] = useState(null);
    const [restaurants, setRestaurants] = useState([]);
    const [touristAttractions, setTouristAttractions] = useState([]);
    const [bars, setBars] = useState([]);
    const [sights, setSights] = useState([]);

    useEffect(() => {
        if (coordinates) {
            const [lat, long] = coordinates.replace(/[<>]/g, '').split(', ');
            setLatitude(lat);
            setLongitude(long);

            // Fetch nearby restaurants
            axios.get(`https://eis.dke.univie.ac.at/locationSearch/api/sparql/nearby-restaurants`, { params: { latitude: lat, longitude: long } })
                .then(response => setRestaurants(response.data))
                .catch(error => console.error('Error fetching restaurants:', error));

            // Fetch nearby tourist attractions
            axios.get(`https://eis.dke.univie.ac.at/locationSearch/api/sparql/nearby-tourist-attractions`, { params: { latitude: lat, longitude: long } })
                .then(response => setTouristAttractions(response.data))
                .catch(error => console.error('Error fetching tourist attractions:', error));

            // Fetch nearby bars
            axios.get(`https://eis.dke.univie.ac.at/locationSearch/api/sparql/nearby-bars`, { params: { latitude: lat, longitude: long } })
                .then(response => setBars(response.data))
                .catch(error => console.error('Error fetching bars:', error));

            // Fetch nearby sights
            axios.get(`https://eis.dke.univie.ac.at/locationSearch/api/sparql/nearby-sights`, { params: { latitude: lat, longitude: long } })
                .then(response => setSights(response.data))
                .catch(error => console.error('Error fetching sights:', error));
        }
    }, [coordinates]);

    const renderList = (title, items) => (
        <div className="category-section">
            <h3 className="category-title">{title}</h3>
            <ul className="category-list" style={{ listStyleType: 'none', paddingLeft: 0 }}>
                {items.map((item, index) => (
                    <li
                        key={index}
                        className="category-list-item btn btn-outline-light btn-lg px-4"
                        style={{
                            marginBottom: '10px',
                            transition: 'background-color 0.3s',
                            textAlign: 'left',
                            display: 'block'
                        }}
                    >
                        <a
                            href={`https://www.google.com/maps/search/?api=1&query=${item.placeLabel}`}
                            target="_blank"
                            rel="noopener noreferrer"
                            className="category-link"
                            style={{
                                display: 'block'
                            }}
                        >
                            {item.placeLabel} - {item.distance}m
                        </a>
                    </li>
                ))}
            </ul>
        </div>
    );

    return (
        <div>
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
                        <div className="col-lg-3 text-white">
                            {renderList('Restaurants, Cafes', restaurants)}
                        </div>
                        <div className="col-lg-3 text-white">
                            {renderList('Bars, Clubs, Pubs', bars)}
                        </div>
                        <div className="col-lg-3 text-white">
                            {renderList('Tourist Attractions', touristAttractions)}
                        </div>
                        <div className="col-lg-3 text-white">
                            {renderList('Sights', sights)}
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
            </div>с
        </div>
    );
};

export default Categories;
