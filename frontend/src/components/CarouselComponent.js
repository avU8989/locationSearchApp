import React from 'react';
import { Carousel } from 'react-bootstrap';

const CarouselComponent = ({ images, abstract, error }) => {
  return (
    <Carousel>
      {images.map((image, index) => (
        <Carousel.Item key={index}>
          <img
            className="d-block w-100"
            src={image[0]}
            alt={`Slide ${index + 1}`}
            style={{ filter: 'brightness(68%)' }} // This dims the background image
          />
          <Carousel.Caption>
            <div>
              <p className="lead text-white mb-4">
                {abstract && (
                  <div style={{ color: "white", fontSize: "30px"}}>
                    <p>{abstract}</p>
                  </div>
                )}
                {error && (
                  <div style={{ color: "red" }}>
                    <p>{error}</p>
                  </div>
                )}
              </p>
            </div>
            <a
              className="text-center"
              style={{ color: "white", paddingTop: "10px" }}
              href="/products"
            >
              Mehr Informationen
              <i className="bi bi-arrow-right"></i>
            </a>
          </Carousel.Caption>
        </Carousel.Item>
      ))}
    </Carousel>
  );
};

export default CarouselComponent;
