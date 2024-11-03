import React, { useState } from 'react';

const CoordinateForm = ({ onSubmit }) => {
    const [latitude, setLatitude] = useState('');
    const [longitude, setLongitude] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit({ latitude, longitude });
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>
                    Latitude:
                    <input
                        type="text"
                        value={latitude}
                        onChange={(e) => setLatitude(e.target.value)}
                    />
                </label>
            </div>
            <div>
                <label>
                    Longitude:
                    <input
                        type="text"
                        value={longitude}
                        onChange={(e) => setLongitude(e.target.value)}
                    />
                </label>
            </div>
            <button type="submit">Submit Coordinates</button>
        </form>
    );
};

export default CoordinateForm;
