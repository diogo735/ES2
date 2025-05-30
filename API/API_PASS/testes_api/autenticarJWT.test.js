// 1. Mock da função de logging
jest.mock('../utils/log', () => ({
  registarLog: jest.fn()
}));

let autenticarJWT;
let registarLog;

beforeEach(() => {
  jest.resetModules(); 
  registarLog = require('../utils/log').registarLog;
  autenticarJWT = require('../middleware/autenticarJWT').autenticarJWT;
});

test('Deve chamar registarLog ao falhar autenticação sem token', async () => {
  const req = { headers: {} };
  const res = {
    status: jest.fn().mockReturnThis(),
    json: jest.fn()
  };
  const next = jest.fn();

  await autenticarJWT(req, res, next);

  expect(registarLog).toHaveBeenCalledWith(expect.objectContaining({
    acao: 'acesso_negado_token',
    sucesso: false
  }));
});
