// controllers/api_controllers.js
const db = require('../db');
const { registarLog } = require('../utils/log');
const { encriptar, desencriptar } = require('../utils/crypto');
const { validarPassword } = require('../utils/validar_password');

async function criarPassword(req, res) {
  const appUUID = req.params.uuid; // UUID ao invés de ID direto
  const { password, site } = req.body;
  const userId = req.user.id;
  const username = req.user.username;

  if (!site || typeof site !== 'string' || site.trim().length === 0) {
    await registarLog({
      user_id: userId,
      username,
      acao: 'criar_password',
      descricao: `Campo 'site' inválido ou vazio ao criar password para a app UUID ${appUUID}`,
      sucesso: false
    });

    return res.status(400).json({ error: "O campo 'site' é obrigatório e deve ser uma string válida." });
  }

  try {
    const appResult = await db.query('SELECT * FROM apps WHERE uuid = $1', [appUUID]);
    const app = appResult.rows[0];

    if (!app) {
      await registarLog({
        user_id: userId,
        username,
        acao: 'criar_password',
        descricao: `Tentou criar password para app UUID ${appUUID}, mas a app não existe.`,
        sucesso: false
      });
      return res.status(404).json({ error: "App não existe" });
    }

    if (app.user_id !== userId) {
      await registarLog({
        user_id: userId,
        username,
        acao: 'criar_password',
        descricao: `Tentou criar password para a app UUID ${appUUID} sem permissão.`,
        sucesso: false
      });
      return res.status(403).json({ error: "Sem permissão para esta app" });
    }

    if (!validarPassword(password)) {
      await registarLog({
        user_id: userId,
        username,
        acao: 'criar_password',
        descricao: `Password fraca rejeitada para app UUID ${appUUID}`,
        sucesso: false
      });
      return res.status(400).json({
        error: "Password insegura. Use no mínimo 8 caracteres, incluindo maiúsculas, minúsculas e números."
      });
    }

    const encryptedPassword = encriptar(password);

    await db.query(
      'INSERT INTO passwords (site, password, app_id) VALUES ($1, $2, $3)',
      [site, encryptedPassword, app.id]
    );

    await registarLog({
      user_id: userId,
      username,
      acao: 'criar_password',
      descricao: `Criou password (encriptada) para o site "${site}" na app UUID ${appUUID}`
    });

    res.status(201).json({ message: 'Password criada com sucesso.' });
  } catch (error) {
    console.error(error);

    await registarLog({
      user_id: userId,
      username,
      acao: 'criar_password',
      descricao: `Erro interno ao criar password para o site "${site}" na app UUID ${appUUID}`,
      sucesso: false
    });

    res.status(500).json({ error: "Erro interno ao criar password" });
  }
}
async function atualizarPassword(req, res) {
  const passwordUUID = req.params.uuid;
  const { password } = req.body;
  const userId = req.user.id;
  const username = req.user.username;

  try {
    const result = await db.query(
      `SELECT p.*, a.user_id 
       FROM passwords p 
       JOIN apps a ON p.app_id = a.id 
       WHERE p.uuid = $1`, [passwordUUID]
    );
    const pass = result.rows[0];

    if (!pass) {
      await registarLog({
        user_id: userId,
        username,
        acao: 'atualizar_password',
        descricao: `Tentou atualizar password UUID ${passwordUUID}, mas ela não existe.`,
        sucesso: false
      });

      return res.status(404).json({ error: "Password não existe" });
    }

    if (pass.user_id !== userId) {
      await registarLog({
        user_id: userId,
        username,
        acao: 'atualizar_password',
        descricao: `Tentou atualizar password UUID ${passwordUUID} sem permissão.`,
        sucesso: false
      });

      return res.status(403).json({ error: "Sem permissão para esta password" });
    }

    if (!validarPassword(password)) {
      await registarLog({
        user_id: userId,
        username,
        acao: 'atualizar_password',
        descricao: `Password fraca rejeitada para password UUID ${passwordUUID}`,
        sucesso: false
      });

      return res.status(400).json({
        error: "Password insegura. Use no mínimo 8 caracteres, incluindo maiúsculas, minúsculas e números."
      });
    }

    const encryptedPassword = encriptar(password);

    await db.query('UPDATE passwords SET password = $1 WHERE uuid = $2', [encryptedPassword, passwordUUID]);

    await registarLog({
      user_id: userId,
      username,
      acao: 'atualizar_password',
      descricao: `Atualizou a password UUID ${passwordUUID}`
    });

    res.json({ message: 'Password atualizada com sucesso.' });
  } catch (error) {
    console.error(error);

    await registarLog({
      user_id: userId,
      username,
      acao: 'atualizar_password',
      descricao: `Erro interno ao atualizar password UUID ${passwordUUID}`,
      sucesso: false
    });

    res.status(500).json({ error: "Erro ao atualizar password" });
  }
}


async function obterPassword(req, res) {
  const uuid = req.params.uuid;
  const userId = req.user.id;
  const username = req.user.username;

  try {
    const result = await db.query(
      `SELECT p.*, a.user_id 
       FROM passwords p 
       JOIN apps a ON p.app_id = a.id 
       WHERE p.uuid = $1`, [uuid]
    );
    const pass = result.rows[0];

    if (!pass) {
      await registarLog({
        user_id: userId,
        username,
        acao: 'obter_password',
        descricao: `Tentou obter a password UUID ${uuid}, mas ela não existe.`,
        sucesso: false
      });

      return res.status(404).json({ error: "Password não existe" });
    }

    if (pass.user_id !== userId) {
      await registarLog({
        user_id: userId,
        username,
        acao: 'obter_password',
        descricao: `Tentou obter a password UUID ${uuid} sem permissão.`,
        sucesso: false
      });

      return res.status(403).json({ error: "Sem permissão para esta password" });
    }

    let passwordDesencriptada;
    try {
      passwordDesencriptada = desencriptar(pass.password);
    } catch (error) {
      console.error('Erro ao desencriptar password:', error);

      await registarLog({
        user_id: userId,
        username,
        acao: 'obter_password',
        descricao: `Erro ao desencriptar password UUID ${uuid}`,
        sucesso: false
      });

      return res.status(500).json({ error: "Erro ao desencriptar password" });
    }

    await registarLog({
      user_id: userId,
      username,
      acao: 'obter_password',
      descricao: `Acedeu à password UUID ${uuid}`
    });

    res.json({ site: pass.site, password: passwordDesencriptada });
  } catch (error) {
    console.error(error);

    await registarLog({
      user_id: userId,
      username,
      acao: 'obter_password',
      descricao: `Erro interno ao obter password UUID ${uuid}`,
      sucesso: false
    });

    res.status(500).json({ error: "Erro ao obter password" });
  }
}


async function criarApp(req, res) {
  const { nome } = req.body;
  const userId = req.user.id;
  const username = req.user.username;

  if (!nome || typeof nome !== 'string' || nome.trim().length === 0) {
  return res.status(400).json({ error: "O campo 'nome' é obrigatório e deve ser uma string válida." });
}


  try {
    await db.query('INSERT INTO apps (nome, user_id) VALUES ($1, $2)', [nome, userId]);

    await registarLog({
      user_id: userId,
      username,
      acao: 'criar_app',
      descricao: `Criou a aplicação "${nome}"`
    });

    res.status(201).json({ message: 'App criada com sucesso.' });
  } catch (error) {
    console.error(error);

    await registarLog({
      user_id: userId,
      username,
      acao: 'criar_app',
      descricao: `Erro ao criar a app "${nome}"`,
      sucesso: false
    });

    res.status(500).json({ error: "Erro ao criar app" });
  }
}

async function listarApps(req, res) {
  const userId = req.user.id;
  const role = req.user.role;

  try {
    let result;
    if (role === 'admin') {
     result = await db.query('SELECT * FROM apps ORDER BY id');
    } else {
      result = await db.query('SELECT * FROM apps WHERE user_id = $1 ORDER BY id', [userId]);
    }
    res.json(result.rows);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: "Erro ao listar apps" });
  }
}


module.exports = {
    criarPassword,
    atualizarPassword,
    obterPassword,
    criarApp,
    listarApps
};
