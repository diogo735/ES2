// routes/apiRoutes.js
const express = require('express');
const router = express.Router();
const { autenticarJWT } = require('../middleware/autenticarJWT');
const controller = require('../controllers/api_controllers');
const { verificarRole } = require('../middleware/verificar_role');

router.post('/password/by-app/:uuid', autenticarJWT, controller.criarPassword);
router.put('/password/by/:uuid', autenticarJWT, controller.atualizarPassword);
router.get('/password/by/:uuid', autenticarJWT, controller.obterPassword);


// só admins podem ver todas as apps e criálas(user e admin)
router.get('/apps', autenticarJWT, verificarRole('admin'), controller.listarApps);
router.post('/app', autenticarJWT, verificarRole('user', 'admin'), controller.criarApp);

module.exports = router;
