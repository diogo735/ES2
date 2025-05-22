// Teste 1
insomnia.test('Status deve ser 200', () => {
  insomnia.expect(response.status).to.equal(200);
});

// Teste 2
insomnia.test('Content-Type deve ser application/json', () => {
  insomnia.expect(response.contentType).to.include('application/json');
});

// Teste 3 GET /password/by/:uuid
insomnia.test('Estrutura do JSON esperada', () => {
  const schema = {
    type: "object",
    properties: {
      site: { type: "string" },
      password: { type: "string" }
    },
    required: ["site", "password"]
  };

  insomnia.expect(response.body).to.jsonSchema(schema);
});
