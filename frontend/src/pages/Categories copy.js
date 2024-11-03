import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import axios from 'axios';

const CategoriesCOPY = (props) => {
    const location = useLocation();
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
            axios.get(`/locationSearch/api/sparql/nearby-restaurants`, { params: { latitude: lat, longitude: long } })
                .then(response => setRestaurants(response.data))
                .catch(error => console.error('Error fetching restaurants:', error));

            // Fetch nearby tourist attractions
            axios.get(`/locationSearch/api/sparql/nearby-tourist-attractions`, { params: { latitude: lat, longitude: long } })
                .then(response => setTouristAttractions(response.data))
                .catch(error => console.error('Error fetching tourist attractions:', error));

            // Fetch nearby bars
            axios.get(`/locationSearch/api/sparql/nearby-bars`, { params: { latitude: lat, longitude: long } })
                .then(response => setBars(response.data))
                .catch(error => console.error('Error fetching bars:', error));

            // Fetch nearby sights
            axios.get(`/locationSearch/api/sparql/nearby-sights`, { params: { latitude: lat, longitude: long } })
                .then(response => setSights(response.data))
                .catch(error => console.error('Error fetching sights:', error));
        }
    }, [coordinates]);

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
        </div>
    );
};

export default CategoriesCOPY;
