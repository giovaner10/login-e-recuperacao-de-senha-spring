import React, { useState, useEffect } from "react";
import axios from "axios";

function App() {
  // login
  const [email, setEmail] = useState("admin@cwi.com.br");
  const [senha, setSenha] = useState("123456");
  const [usuario, setUsuario] = useState(null);

  // rest
  const [livros, setLivros] = useState([]);

  // api
  const apiUrl = "http://localhost:3000";

  useEffect(() => {
    const buscarUsuario = async () => {
      try {
        let response = await axios.get(`${apiUrl}/usuarios/me`);
        setUsuario(response.data);
      } catch (e) {
        setUsuario(null);
      }
    };

    buscarUsuario();
  }, []);

  const handleLogar = async () => {
    let response = await axios.post(
      `${apiUrl}/login`,
      {},
      {
        auth: {
          username: email,
          password: senha,
        },
      }
    );
    setUsuario(response.data);
  };

  const handleLogout = async () => {
    await axios.post(`${apiUrl}/logout`);
    setUsuario(null);
  };

  const handleRest = async () => {
    const response = await axios.get(`${apiUrl}/livros`);
    setLivros(response.data.content);
  };

  return (
    <div>
      {usuario ? (
        <div>
          <h2>Logado como {usuario.nome}</h2>
          <button onClick={handleLogout}>Logout</button>
          <h3>Rest</h3>
          <button onClick={handleRest}>Enviar</button>
          {livros &&
            livros.map((livro) => <li key={livro.id}>{livro.titulo}</li>)}
        </div>
      ) : (
        <div>
          <h2>Login</h2>
          <label htmlFor="email">Email: </label>
          <input
            id="email"
            type="text"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <br />
          <label htmlFor="senha">Senha: </label>
          <input
            id="senha"
            type="password"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
          />
          <br />
          <button onClick={handleLogar}>Login</button>
        </div>
      )}
    </div>
  );
}

export default App;
