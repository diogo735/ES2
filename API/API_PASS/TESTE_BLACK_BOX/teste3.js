insomnia.test('Status deve ser OK', () => {
  insomnia.expect(insomnia.response.status).to.equal("OK");
});

insomnia.test('Content-Type deve ser application/json', () => {
  const header = insomnia.response.headers.find(
    h => h.key.toLowerCase() === 'content-type'
  );
  insomnia.expect(header && header.value).to.include('application/json');
});

insomnia.test('Mensagem de sucesso ao atualizar password', () => {
  const data = JSON.parse(insomnia.response.body);
  insomnia.expect(data.message).to.equal('Password atualizada com sucesso.');
});
