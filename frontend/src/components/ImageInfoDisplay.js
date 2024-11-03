import React from 'react';
import { Link } from 'react-router-dom';

const mockLocationInfo = {
    address: '1234 Example Street, City, Country',
    latitude: '37.7749',
    longitude: '-122.4194'
};

const mockData = {
    sights: [
        { name: 'Sight 1', distance: 200 },
        { name: 'Sight 2', distance: 400 },
        { name: 'Sight 3', distance: 600 },
        { name: 'Sight 4', distance: 800 },
        { name: 'Sight 5', distance: 1000 },
        { name: 'Sight 6', distance: 1200 },
        { name: 'Sight 7', distance: 1400 },
        { name: 'Sight 8', distance: 1600 },
        { name: 'Sight 9', distance: 1800 },
        { name: 'Sight 10', distance: 2000 },
    ],
    cafes: [
        { name: 'Cafe 1', distance: 150 },
        { name: 'Cafe 2', distance: 300 },
        { name: 'Cafe 3', distance: 450 },
        { name: 'Cafe 4', distance: 600 },
        { name: 'Cafe 5', distance: 750 },
        { name: 'Cafe 6', distance: 900 },
        { name: 'Cafe 7', distance: 1050 },
        { name: 'Cafe 8', distance: 1200 },
        { name: 'Cafe 9', distance: 1350 },
        { name: 'Cafe 10', distance: 1500 },
    ],
    restaurants: [
        { name: 'Restaurant 1', distance: 250 },
        { name: 'Restaurant 2', distance: 500 },
        { name: 'Restaurant 3', distance: 750 },
        { name: 'Restaurant 4', distance: 1000 },
        { name: 'Restaurant 5', distance: 1250 },
        { name: 'Restaurant 6', distance: 1500 },
        { name: 'Restaurant 7', distance: 1750 },
        { name: 'Restaurant 8', distance: 2000 },
        { name: 'Restaurant 9', distance: 2250 },
        { name: 'Restaurant 10', distance: 2500 },
    ],
    bars: [
        { name: 'Bar 1', distance: 300 },
        { name: 'Bar 2', distance: 600 },
        { name: 'Bar 3', distance: 900 },
        { name: 'Bar 4', distance: 1200 },
        { name: 'Bar 5', distance: 1500 },
        { name: 'Bar 6', distance: 1800 },
        { name: 'Bar 7', distance: 2100 },
        { name: 'Bar 8', distance: 2400 },
        { name: 'Bar 9', distance: 2700 },
        { name: 'Bar 10', distance: 3000 },
    ],
    touristAttractions: [
        { name: 'Attraction 1', distance: 350 },
        { name: 'Attraction 2', distance: 700 },
        { name: 'Attraction 3', distance: 1050 },
        { name: 'Attraction 4', distance: 1400 },
        { name: 'Attraction 5', distance: 1750 },
        { name: 'Attraction 6', distance: 2100 },
        { name: 'Attraction 7', distance: 2450 },
        { name: 'Attraction 8', distance: 2800 },
        { name: 'Attraction 9', distance: 3150 },
        { name: 'Attraction 10', distance: 3500 },
    ],
};

const ImageInfoDisplay = ({ imageInfo }) => {
    const categories = Object.keys(mockData);

    return (
        <div>
            <h2>Image Information</h2>
            <p><strong>Location:</strong> {mockLocationInfo.address}</p>
            <p><strong>Latitude:</strong> {mockLocationInfo.latitude}</p>
            <p><strong>Longitude:</strong> {mockLocationInfo.longitude}</p>

            {categories.map((category) => (
                <div key={category}>
                    <h3>{category.charAt(0).toUpperCase() + category.slice(1)}</h3>
                    <ul>
                        {mockData[category].map((place, index) => (
                            <li key={index}>
                                <a
                                    href={`https://www.google.com/maps/search/?api=1&query=${place.name}`}
                                    target="_blank"
                                    rel="noopener noreferrer"
                                >
                                    {place.name} - {place.distance}m
                                </a>
                            </li>
                        ))}
                    </ul>
                </div>
            ))}
            <button>
                <Link to="/" style={{ color: 'white', textDecoration: 'none' }}>Back to Home</Link>
            </button>
        </div>
    );
};

export default ImageInfoDisplay;
