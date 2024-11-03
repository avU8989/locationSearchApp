import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

const GPSResultsDisplay = ({ result }) => {

    const [restaurants, setRestaurants] = useState([]);
    const [touristAttractions, setTouristAttractions] = useState([]);
    const [bars, setBars] = useState([]);
    const [sights, setSights] = useState([]);

    useEffect(() => {
        if (result && result.data) {
            const { latitude, longitude } = result.data;

            // Fetch nearby restaurants
            fetch(`/locationSearch/api/sparql/nearby-restaurants?latitude=${latitude}&longitude=${longitude}`)
                .then(response => response.json())
                .then(data => setRestaurants(data))
                .catch(error => console.error('Error fetching restaurants:', error));

            // Fetch nearby tourist attractions
            fetch(`/locationSearch/api/sparql/nearby-tourist-attractions?latitude=${latitude}&longitude=${longitude}`)
                .then(response => response.json())
                .then(data => setTouristAttractions(data))
                .catch(error => console.error('Error fetching tourist attractions:', error));

            // Fetch nearby bars
            fetch(`/locationSearch/api/sparql/nearby-bars?latitude=${latitude}&longitude=${longitude}`)
                .then(response => response.json())
                .then(data => setBars(data))
                .catch(error => console.error('Error fetching bars:', error));

            // Fetch nearby sights
            fetch(`/locationSearch/api/sparql/nearby-sights?latitude=${latitude}&longitude=${longitude}`)
                .then(response => response.json())
                .then(data => setSights(data))
                .catch(error => console.error('Error fetching sights:', error));
        }
    }, [result]);

    const renderList = (title, items) => (
        <div>
            <h3>{title}</h3>
            <ul>
                {items.map((item, index) => (
                    <li key={index}>
                        <a
                            href={`https://www.google.com/maps/search/?api=1&query=${item.placeLabel}`}
                            target="_blank"
                            rel="noopener noreferrer"
                        >
                            {item.placeLabel} - {item.distance} meters
                        </a>
                    </li>
                ))}
            </ul>
        </div>
    );

    return (
        <div>
            <h2>Nearby Locations</h2>
            {renderList('Restaurants, Cafes', restaurants)}
            {renderList('Bars, Clubs, Pubs', bars)}
            {renderList('Tourist Attractions', touristAttractions)}
            {renderList('Sights', sights)}
            <button>
                <Link to="/" style={{ color: 'white', textDecoration: 'none' }}>Back to Home</Link>
            </button>
        </div>
    );
};

export default GPSResultsDisplay;
