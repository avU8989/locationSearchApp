import React from 'react';
import { Carousel } from 'react-bootstrap';
import 'lightbox2/dist/css/lightbox.min.css';
import 'lightbox2/dist/js/lightbox-plus-jquery.min.js';

const CarouselComponentSights = ({ sights, images, error }) => {
  return (
    <div>
      <Carousel>
        {images.map((imageGroup, index) => {
          const sight = sights[index];
          console.log('Sight:', sight); // Log the sight
          console.log('Image Group:', imageGroup); // Log the imageGroup array

          // Check if the first element is an array
          if (!Array.isArray(imageGroup)) {
            console.warn('Unexpected structure in imageGroup:', imageGroup);
            return null;
          }

          return imageGroup.map((image, imgIndex) => {
            if (!image[0]) return null; // Skip if the image URL is null
            console.log('Image:', image); // Log each image

            return (
              <Carousel.Item key={`${index}-${imgIndex}`}>
                <a href={image[0]} data-lightbox="sights-gallery" data-title={image[1] || "No description"}>
                  <img
                    className="d-block w-100 rounded"
                    src={image[0]} // Access the image URL
                    alt={`Slide ${index + 1}`}
                    style={{ filter: 'brightness(68%)', borderRadius: '10px' }} // This dims the background image and adds rounded edges
                  />
                </a>
                <Carousel.Caption>
                  <div>
                    <h3>{sight.placeLabel}</h3> {/* Display the place label */}
                    {image[1] && ( // Access the image description
                      <div style={{ color: "white", fontSize: "20px", marginTop: "10px" }}>
                        <p>{image[1]}</p>
                      </div>
                    )}
                  </div>
                  <a
                    className="text-center"
                    style={{ color: "white", paddingTop: "10px" }}
                    href={`https://www.google.com/maps/search/?api=1&query=${sight.placeLabel}`}
                  >
                    
                    View on Google Maps
                    <i className="bi bi-arrow-right"></i>
                  </a>
                </Carousel.Caption>
              </Carousel.Item>
            );
          });
        })}
      </Carousel>
      {error && (
        <div style={{ color: 'red', marginTop: '10px' }}>
          <p>{error}</p>
        </div>
      )}
    </div>
  );
};

export default CarouselComponentSights;
