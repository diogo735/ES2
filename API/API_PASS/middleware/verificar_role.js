// middleware/verificar_role.js
const { registarLog } = require('../utils/log');
function verificarRole(...rolesPermitidos) {
  return function (req, res, next) {
    try {
      const role = req.user?.role;
      const userId = req.user?.id;
      const username = req.user?.username || 'desconhecido';

      if (!role) {
        registarLog({
          user_id: userId,
          username,
          acao: 'acesso_negado_role_ausente',
          descricao: 'Tentativa de acesso sem role',
          sucesso: false
        });

        return res.status(401).json({ error: 'Autenticação necessária (sem role)' });
      }

      if (!rolesPermitidos.includes(role)) {
        registarLog({
          user_id: userId,
          username,
          acao: 'acesso_negado_role_nao_autorizado',
          descricao: `Tentativa de acesso com role não autorizado: ${role}`,
          sucesso: false
        });

        return res.status(403).json({ error: 'Acesso negado. Role não autorizado para esta ação.' });
      }

      next();
    } catch (err) {
      console.error('Erro no middleware de verificação de role:', err);
      res.status(500).json({ error: 'Erro interno ao verificar permissões' });
    }
  };
}

module.exports = { verificarRole };
