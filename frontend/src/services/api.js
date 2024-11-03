import axios from 'axios';

const API_URL = 'https://eis.dke.univie.ac.at/locationSearch';

export const fetchSubjectAbstract = async (locationName) => {
  try {
    const response = await axios.get(`${API_URL}/api/sparql/dbpedia/subject/abstract`, {
      params: { locationName }
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching subject abstract:', error);
    throw error;
  }
};

export const fetchSubjectComment = async (locationName) => {
  try {
    const response = await axios.get(`${API_URL}/api/sparql/dbpedia/subject/comment`, {
      params: { locationName }
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching subject abstract:', error);
    throw error;
  }
};

export const fetchSubjectAbstractWithResource = async (locationName) => {
  try {
    const response = await axios.get(`${API_URL}/api/sparql/dbpedia/subjectWithResource/abstract`, {
      params: { locationName }
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching subject abstract:', error);
    throw error;
  }
};


export const fetchImage = async (text) => {
    try {
      const response = await axios.get(`${API_URL}/location`, {
        params: { text }
      });
      return response.data;
    } catch (error) {
      console.error('Error fetching image:', error);
      throw error;
    }
  };

  export const fetchImageEng = async (text) => {
    try {
      const response = await axios.get(`${API_URL}/location/eng`, {
        params: { text }
      });
      return response.data;
    } catch (error) {
      console.error('Error fetching image:', error);
      throw error;
    }
  };