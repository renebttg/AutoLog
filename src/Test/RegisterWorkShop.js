import React, { useState } from 'react';
import axios from 'axios';

const RegisterWorkshop = () => {
  const [formData, setFormData] = useState({
    name: 'Exemplo Oficina',
    cnpj: '122245678901234',
    email: 'oficina4@example.com',
    password: '123456',
    phone: '(00) 1234-5678',
    nameWorkshop: 'Oficina Mecânica',
    addressWorkshop: 'Rua Exemplo, 123',
  });

  const [response, setResponse] = useState(null);
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post('http://localhost:8080/auth/register', formData, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
      setResponse(res.data);
      setError(null);
    } catch (err) {
      setError(err.response ? err.response.data : 'Error: Unable to register');
      setResponse(null);
    }
  };

  return (
    <div>
      <h2>Register Workshop</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Name:
          <input type="text" name="name" value={formData.name} onChange={handleChange} />
        </label>
        <br />
        <label>
          CNPJ:
          <input type="text" name="cnpj" value={formData.cnpj} onChange={handleChange} />
        </label>
        <br />
        <label>
          Email:
          <input type="email" name="email" value={formData.email} onChange={handleChange} />
        </label>
        <br />
        <label>
          Password:
          <input type="password" name="password" value={formData.password} onChange={handleChange} />
        </label>
        <br />
        <label>
          Phone:
          <input type="text" name="phone" value={formData.phone} onChange={handleChange} />
        </label>
        <br />
        <label>
          Workshop Name:
          <input type="text" name="nameWorkshop" value={formData.nameWorkshop} onChange={handleChange} />
        </label>
        <br />
        <label>
          Workshop Address:
          <input type="text" name="addressWorkshop" value={formData.addressWorkshop} onChange={handleChange} />
        </label>
        <br />
        <button type="submit">Register</button>
      </form>
      {response && <div>Success: {JSON.stringify(response)}</div>}
      {error && <div>Error: {error}</div>}
    </div>
  );
};

export default RegisterWorkshop;
