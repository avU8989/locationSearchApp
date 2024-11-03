import React, { useState } from 'react';

const ImageUploadForm = ({ onSubmit }) => {
    const [image, setImage] = useState(null);

    const handleImageChange = (e) => {
        setImage(e.target.files[0]);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (image) {
            onSubmit(image);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>
                    Upload Image:
                    <input
                        type="file"
                        accept="image/*"
                        onChange={handleImageChange}
                    />
                </label>
            </div>
            <button type="submit">Submit Image</button>
        </form>
    );
};

export default ImageUploadForm;
