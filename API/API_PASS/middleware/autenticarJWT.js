require('dotenv').config();
const jwt = require('jsonwebtoken');
const SECRET_KEY = process.env.SECRET_KEY;
const { registarLog } = require('../utils/log');

function autenticarJWT(req, res, next) {
  try {
    const authHeader = req.headers.authorization;

    if (!authHeader || !authHeader.startsWith('Bearer ')) {
      registarLog({
        user_id: null,
        username: 'desconhecido',
        acao: 'acesso_negado_token',
        descricao: 'Tentativa de acesso sem token ou formato inválido',
        sucesso: false
      });

      return res.status(401).json({ error: 'Token não fornecido ou formato inválido' });
    }

    const token = authHeader.split(' ')[1];

    jwt.verify(token, SECRET_KEY, (err, user) => {
      if (err) {
        registarLog({
          user_id: null,
          username: 'desconhecido',
          acao: 'token_invalido',
          descricao: 'Token inválido ou expirado',
          sucesso: false
        });

        return res.status(403).json({ error: 'Token inválido ou expirado' });
      }

      req.user = user;
      next();
    });
  } catch (err) {
    console.error('Erro no middleware JWT:', err);
    res.status(500).json({ error: 'Erro interno na autenticação' });
  }
}
module.exports = {
  autenticarJWT,
  SECRET_KEY
};
