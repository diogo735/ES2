// servidor.js
const express = require('express');
const rateLimit = require('express-rate-limit');
const app = express();
const apiRoutes = require('./routes/api_rotas');
const db = require('./db'); 
const PORT = 3000;

//  (proteção contra força bruta)
const limiter = rateLimit({
  windowMs: 15 * 60 * 1000, 
  max: 100, 
  message: { error: 'Muitas requisições — tente novamente mais tarde.' }
});

app.use(limiter); // aplica o limiter a todas as rotas
app.use(express.json());
app.use('/', apiRoutes);


// Middleware global para erros não tratados
app.use((err, req, res, next) => {
  console.error('Erro não tratado:', err);
  res.status(500).json({ error: 'Erro interno inesperado' });
});



db.query('SELECT NOW()')
  .then(res => {
    console.log('✅ Ligação ao PostgreSQL bem-sucedida!');
    app.listen(PORT, () => {
      console.log(`🚀 API a rodar no url: http://localhost:${PORT}`);
    });
  })
  .catch(err => {
    console.error('❌ Erro ao ligar à base de dados:', err.message);
    process.exit(1); 
  });
