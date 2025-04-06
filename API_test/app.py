from flask import Flask, request, jsonify, make_response
from flask_cors import CORS
import pandas as pd
import json

app = Flask(__name__)
CORS(app)

df = pd.read_csv(r'C:\Users\biass\Documents\API Test\Relatorio_cadop_convertido2.0.csv')

# @app.route('/buscar', methods=['GET'])
# def buscar_operadora():
#     termo = request.args.get('q', '').lower()
#     if not termo:
#         return jsonify({'erro': 'Você precisa passar o parâmetro q na URL'}), 400

#     coluna_operadora = "Nome_Fantasia"  
    
#     if coluna_operadora not in df.columns:
#         return jsonify({'erro': f'A coluna "{coluna_operadora}" não foi encontrada no arquivo CSV'}), 400
    
   
#     resultados = df[df[coluna_operadora].astype(str).str.lower().str.contains(termo, na=False)]

 
#     print("Resultados encontrados:", resultados.to_dict(orient='records'))

#     return jsonify(resultados.where(pd.notnull(resultados), None).to_dict(orient='records')), 200


# @app.route('/buscar', methods=['GET'])
# def buscar_operadora():
#     termo = request.args.get('q', '').lower()
#     if not termo:
#         return jsonify({'erro': 'Você precisa passar o parâmetro q na URL'}), 400

#     coluna_operadora = "Nome_Fantasia"
#     if coluna_operadora not in df.columns:
#         return jsonify({'erro': f'A coluna "{coluna_operadora}" não foi encontrada no arquivo CSV'}), 400

#     resultados = df[df[coluna_operadora].astype(str).str.lower().str.contains(termo, na=False)]
#     lista_resultados = resultados.where(pd.notnull(resultados), None).to_dict(orient='records')

#     return jsonify(lista_resultados)
@app.route('/buscar', methods=['GET'])
def buscar_operadora():
    termo = request.args.get('q', '').lower()
    if not termo:
        return jsonify({'erro': 'Você precisa passar o parâmetro q na URL'}), 400

    coluna_operadora = "Nome_Fantasia"
    if coluna_operadora not in df.columns:
        return jsonify({'erro': f'A coluna "{coluna_operadora}" não foi encontrada no arquivo CSV'}), 400

    resultados = df[df[coluna_operadora].astype(str).str.lower().str.contains(termo, na=False)]
    lista_resultados = resultados.where(pd.notnull(resultados), None).to_dict(orient='records')

    return lista_resultados

if __name__ == '__main__':
     app.run(debug=True)