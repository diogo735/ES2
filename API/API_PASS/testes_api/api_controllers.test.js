process.env.AES_KEY = Buffer.from('12345678901234567890123456789012').toString('base64'); // 32 bytes
process.env.AES_IV = Buffer.from('1234567890123456').toString('base64'); // 16 bytes




// POST /password/by-app/:uuid → criarPassword()
//→ if (!site || typeof site !== 'string' || site.trim().length === 0) [V F F] - api_controllers.js linha 20
/**
 * Endpoint: POST /password/by-app/:uuid
 * Função: criarPassword()
 * Estrutura testada: if (!site || typeof site !== 'string' || site.trim().length === 0)
 * Condições do input: site = '' → [V F F]
 * Esperado: retorna 400 Bad Request
 * Linha: api_controllers.js linha 20
 */
const { criarPassword } = require('../controllers/api_controllers');

const resMock = () => ({
  status: jest.fn().mockReturnThis(),
  json: jest.fn()
});

test('POST /password/by-app/:uuid - testa site inválido (vazio) → if (!site || ...) [V F F] - linha 20', async () => {
  const req = {
    params: { uuid: 'fake-uuid' },
    body: { site: '', password: 'SenhaValida123' },
    user: { id: 1, username: 'joao' }
  };
  const res = resMock();
  await criarPassword(req, res);
  expect(res.status).toHaveBeenCalledWith(400);
});


// PUT /password/by/:uuid → atualizarPassword()
//→ if (!pass) [V] - api_controllers.js linha 94
/**
 * Endpoint: PUT /password/by/:uuid
 * Função: atualizarPassword()
 * Estrutura testada: if (!pass)
 * Condições do input: resultado do db.query = []
 * Esperado: retorna 404 Password não existe
 * Linha: api_controllers.js linha 94
 */

const { atualizarPassword } = require('../controllers/api_controllers');

test('PUT /password/by/:uuid - password inexistente → if (!pass) [V] - linha 94', async () => {
  const req = {
    params: { uuid: 'invalido' },
    body: { password: 'NovaSenha123' },
    user: { id: 1, username: 'joao' }
  };
  const res = resMock();

  // simular resultado vazio do banco
  const db = require('../db');
  jest.spyOn(db, 'query').mockResolvedValueOnce({ rows: [] });

  await atualizarPassword(req, res);
  expect(res.status).toHaveBeenCalledWith(404);
});


//GET /password/by/:uuid → obterPassword()
//→ if (pass.user_id !== userId) [V] - api_controllers.js linha 158
/**
 * Endpoint: GET /password/by/:uuid
 * Função: obterPassword()
 * Estrutura testada: if (pass.user_id !== userId)
 * Condições do input: user_id ≠ pass.user_id
 * Esperado: retorna 403 Forbidden
 * Linha: api_controllers.js linha 158
 */

const { obterPassword } = require('../controllers/api_controllers');

test('GET /password/by/:uuid - acesso não autorizado → if (pass.user_id !== userId) [V] - linha 158', async () => {
  const req = {
    params: { uuid: 'uuid-pw' },
    user: { id: 1, username: 'joao' }
  };
  const res = resMock();

  const db = require('../db');
  jest.spyOn(db, 'query').mockResolvedValueOnce({
    rows: [{ user_id: 999, password: 'dummy' }]
  });

  await obterPassword(req, res);
  expect(res.status).toHaveBeenCalledWith(403);
});


// POST /app → criarApp()
//→ if (!nome || typeof nome !== 'string' || nome.trim().length === 0) [V F F] - api_controllers.js linha 234
/**
 * Endpoint: POST /app
 * Função: criarApp()
 * Estrutura testada: if (!nome || typeof nome !== 'string' || nome.trim().length === 0)
 * Condições do input: nome = '' → [V F F]
 * Esperado: retorna 400 Bad Request
 * Linha: api_controllers.js linha 234
 */

const { criarApp } = require('../controllers/api_controllers');

test('POST /app - nome vazio → if (!nome || ...) [V F F] - linha 234', async () => {
  const req = {
    body: { nome: '' },
    user: { id: 1, username: 'joao' }
  };
  const res = resMock();
  await criarApp(req, res);
  expect(res.status).toHaveBeenCalledWith(400);
});


//GET /apps → listarApps()
//→ if (role === 'admin') [V] - api_controllers.js linha 260
/**
 * Endpoint: GET /apps
 * Função: listarApps()
 * Estrutura testada: if (role === 'admin')
 * Condições do input: role = 'admin' → [V]
 * Esperado: retorna lista de todas as apps
 * Linha: api_controllers.js linha 260
 */

const { listarApps } = require('../controllers/api_controllers');

test('GET /apps - role admin → if (role === "admin") [V] - linha 260', async () => {
  const req = {
    user: { id: 1, role: 'admin' }
  };
  const res = resMock();

  const db = require('../db');
  jest.spyOn(db, 'query').mockResolvedValueOnce({ rows: [{ id: 1, nome: 'App 1' }] });

  await listarApps(req, res);
  expect(res.json).toHaveBeenCalledWith([{ id: 1, nome: 'App 1' }]);
});


// POST /import/apps → importarApps()
//→ Stub: simula retorno da API externa com dados fixos - api_controllers.js linha 286

const { importarApps } = require('../controllers/api_controllers');

test('POST /import/apps - importa apps simuladas com sucesso → stub de dados externos - linha 286', async () => {
  const req = {
    user: { id: 1, username: 'joao' }
  };
  const res = resMock();

  const db = require('../db');
  const dbMock = jest.spyOn(db, 'query').mockResolvedValue();

  await importarApps(req, res);

  expect(dbMock).toHaveBeenCalledWith(
    expect.stringContaining('INSERT INTO apps'),
    expect.arrayContaining(['Facebook', 'fb-123'])
  );
  expect(res.status).toHaveBeenCalledWith(201);
});