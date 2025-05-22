const db = require('../db');

async function registarLog({ user_id, username, acao, descricao, sucesso = true }) {
  try {
    await db.query(
      `INSERT INTO logs (user_id, username, acao, descricao, sucesso)
       VALUES ($1, $2, $3, $4, $5)`,
      [user_id || null, username || 'desconhecido', acao, descricao, sucesso]
    );
  } catch (error) {
    console.error('Erro ao registar log:', error);
  }
}

module.exports = { registarLog };
