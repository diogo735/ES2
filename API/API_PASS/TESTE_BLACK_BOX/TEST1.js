// Teste 1: Status textual
insomnia.test('Status deve ser OK', () => {
  insomnia.expect(insomnia.response.status).to.equal("OK");
});

// Teste 2: Content-Type JSON
insomnia.test('Content-Type deve ser application/json', () => {
  const header = insomnia.response.headers.find(
    h => h.key.toLowerCase() === 'content-type'
  );
  insomnia.expect(header && header.value).to.include('application/json');
});

// Teste 3: Estrutura básica do JSON
insomnia.test('Primeiro objeto tem campos esperados', () => {
  const data = JSON.parse(insomnia.response.body);
  insomnia.expect(data[0]).to.have.property('id');
  insomnia.expect(data[0]).to.have.property('nome');
  insomnia.expect(data[0]).to.have.property('user_id');
  insomnia.expect(data[0]).to.have.property('uuid');
});
