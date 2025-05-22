// Teste 1
insomnia.test('Status deve ser 200', () => {
  insomnia.expect(response.status).to.equal(200);
});

// Teste 2
insomnia.test('Content-Type deve ser application/json', () => {
  insomnia.expect(response.contentType).to.include('application/json');
});

// Teste 3 GET /apps
insomnia.test('Estrutura do JSON esperada', () => {
  const schema = {
    type: "array",
    items: {
      type: "object",
      properties: {
        id: { type: "number" },
        nome: { type: "string" },
        user_id: { type: "number" },
        uuid: { type: "string" }
      },
      required: ["id", "nome", "user_id", "uuid"]
    }
  };

  insomnia.expect(response.body).to.jsonSchema(schema);
});
