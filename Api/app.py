import math
import uuid
from flask import jsonify, Flask, request
from notifier import init_FCM, send_notification
from usuarios import USERS
from reservas import RESERVAS
from dummy_data import PROVIDERS

app = Flask("serviceMatch")

init_FCM()

@app.route('/healthcheck', methods=['GET'])
def healthcheck():
    return {"message": "i'm alive"}, 200


@app.route('/tipoProfesion', methods=['GET'])
def tipoServicio():
    try:
        profesiones = [
            'Electricista'
            , 'Plomeria'
            , 'Carpinteria'
            , 'Albañileria'
            , 'Mecanica'
            , 'Jardineria'
            , 'Herreria'
            , 'Zapateria'
            , 'Costureria'
            , 'Pintura'
            , 'Gasista'
            , 'Limpieza a domicilio'
            , 'Técnico en aire acondicionado'
            , 'Técnico en refrigeración'
            , 'Técnico en computadoras'
            , 'Técnico en electrodomésticos'
        ]

        return jsonify(profesiones), 200
    except Exception as e:
        return {'error': str(e)}, 500


@app.route('/valoraciones', methods=['GET'])
def valoraciones():
    try:
        valoracion = [
             '1', '2', '3',  '4', '5'
        ]

        return jsonify(valoracion), 200
    except Exception as e:
        return {'error': str(e)}, 500


@app.route('/login', methods=['POST'])
def login():
    try:
        data = request.get_json()
        username = data['username']
        password = data['password']
        for user in USERS:
            if user["username"] == username and user["password"] == password:
                return jsonify({'message': 'Login successful'}), 200
        return jsonify({'message': 'Login error'}), 403
    except Exception as e:
        return jsonify({'error': str(e)}), 500


""" @app.route('/zonas', methods=['GET'])
def zonas():
    try:
        zonas = [	
            'Recoleta'
            ,'Palermo'
            ,'San Telmo'
            ,'La Boca  '
            ,'Microcentro'
            ,'Belgrano'
            ,'Caballito'
            ,'Boedo'
            ,'Almagro'
            ,'Villa Crespo'
            ,'Villa Devoto'
            ,'Villa del Parque'
            ,'Núñez'
            ,'Saavedra'
            ,'Flores'
            ,'Villa Lugano'
            ,'Mataderos'
            ,'Parque Chacabuco'
            ,'Liniers'
            ,'Villa Urquiza'
            ,'Coghlan'
            ,'Villa Pueyrredón'
            ,'Balvanera'
            ,'Barracas'
            ,'Villa Riachuelo'
            ,'Parque Patricios'
            ,'Versalles'
            ,'Villa Luro'
            ,'Villa Santa Rita'
            ,'Villa Ortúzar'
            ,'Parque Avellaneda'
            ,'Villa General Mitre'
            ,'Villa Soldati'
            ,'Monte Castro'
            ,'Villa Real	'
            ,'Vélez Sársfield'
            ,'Villa Riachuelo'
            ,'Córdoba'
            ,'Rosario'
            ,'Mendoza'
            ,'Tucumán'
            ,'La Plata'
            ,'Mar del Plata'
            ,'Salta'
            ,'Santa Fe'
            ,'San Juan'
            ,'San Miguel de Tucumán'
            ,'San Luis'
            ,'Bariloche'
            ,'Bahía Blanca'
            ,'Neuquén'
            ,'Posadas'
            ,'Corrientes'
            ,'Resistencia'
            ,'San Salvador de Jujuy'
            ,'Formosa'
            ,'Santa Rosa'
            ,'Paraná'
            ,'Ushuaia'
            ,'Comodoro Rivadavia'
            ,'La Rioja'
            ,'Rawson'
            ,'Catamarca'
            ,'Santiago del Estero'
            ,'San Fernando del Valle de Catamarca'
            ,'Río Gallegos'
            ,'Viedma'
        ]	
        return jsonify(zonas),200
    except Exception as e:
        return "Error", 500 """


@app.route('/recomendados', methods=['GET'])
def recomendados():
    try:
        recomendados = [{
            "id": 0,
            "path": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQF5TcVFjPc_Z0ZdLUAA2Df6uTrJL1C5Al4-w&usqp=CAU",
            "nombre": "Joaquin Benitez",
            "precioCategoria": "$$$$",
            "ubicación": "Palermo",
            "rol": "Plomeria"
        }]
        return jsonify(recomendados), 200
    except Exception as _:
        return "Error", 500


@app.route('/usuarios', methods=['GET'])
def comentarios():
    try:
        comentarios = {
            'usuario1': {
                'name': 'Fernando',
                'lastname': 'Rabinovich',
                'profesion': 'profesion',
                'puntajePromedio': 800,
                'comentarios': {
                    'comentario1': {
                        'comentarioId': 1,
                        'mensaje': 'Mensaje 1',
                        'puntajeAsignado': 100
                    },
                    'comentario2': {
                        'comentarioId': 2,
                        'mensaje': 'Mensaje 2',
                        'puntajeAsignado': 100
                    }
                }
            },
            'usuario2': {
                'name': 'Pedro',
                'lastname': 'Bruno',
                'profesion': 'profesion',
                'puntajePromedio': 800,
                'comentarios': {
                    'comentario1': {
                        'comentarioId': 1,
                        'mensaje': 'Mensaje 1',
                        'puntajeAsignado': 100
                    },
                    'comentario2': {
                        'comentarioId': 2,
                        'mensaje': 'Mensaje 2',
                        'puntajeAsignado': 100
                    }
                }
            },
            'usuario3': {
                'name': 'Hugo',
                'lastname': 'Peykovick',
                'profesion': 'profesion',
                'puntajePromedio': 800,
                'comentarios': {
                    'comentario1': {
                        'comentarioId': 1,
                        'mensaje': 'Mensaje 1',
                        'puntajeAsignado': 100
                    },
                    'comentario2': {
                        'comentarioId': 2,
                        'mensaje': 'Mensaje 2',
                        'puntajeAsignado': 100
                    }
                }
            },
            'usuario4': {
                'name': 'Agustin',
                'lastname': 'Saturni',
                'profesion': 'profesion',
                'puntajePromedio': 800,
                'comentarios': {
                    'comentario1': {
                        'comentarioId': 1,
                        'mensaje': 'Mensaje 1',
                        'puntajeAsignado': 100},
                    'comentario2': {
                        'comentarioId': 2,
                        'mensaje': 'Mensaje 2',
                        'puntajeAsignado': 100
                    }
                }
            },
            'usuario5': {
                'name': 'Nicolas',
                'lastname': 'Fuentes',
                'profesion': 'profesion',
                'puntajePromedio': 800,
                'comentarios': {
                    'comentario1': {
                        'comentarioId': 1,
                        'mensaje': 'Mensaje 1',
                        'puntajeAsignado': 100
                    },
                    'comentario2': {
                        'comentarioId': 2,
                        'mensaje': 'Mensaje 2',
                        'puntajeAsignado': 100
                    }
                }
            }
        }
        return comentarios, 200
    except Exception as e:
        return {'error': str(e)}, 500


@app.route('/providers/', methods=['GET'])
def providers():
    try:
        return jsonify(PROVIDERS), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 500


def filterByDistancia(proveedor, latitudCliente, longitudCliente):
    latitudProveedor = proveedor['latitud']
    longitudProveedor = proveedor['longitud']
    rangoMaxProveedor = proveedor['rangoMax']
    if (rangoMaxProveedor > distancia(latitudCliente, longitudCliente, latitudProveedor, longitudProveedor)):
        return True
    else:
        return False

def distancia(lat1, lon1, lat2, lon2):
    R = 6371  # Radio de la Tierra en kilómetros
    dLat = math.radians(lat2 - lat1)
    dLon = math.radians(lon2 - lon1)
    a = math.sin(dLat / 2) * math.sin(dLat / 2) + math.cos(math.radians(lat1)) * math.cos(
        math.radians(lat2)) * math.sin(dLon / 2) * math.sin(dLon / 2)
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    distancia = R * c * 1000  # Distancia en metros
    return distancia

@app.route('/providersByCoordinates', methods=['GET'])
def getProvidersByCoordinates():
    try:
        latitudCliente_str = request.args.get('latitud')
        longitudCliente_str = request.args.get('longitud')
        latitudCliente = float(latitudCliente_str) if latitudCliente_str is not None else None
        longitudCliente = float(longitudCliente_str) if longitudCliente_str is not None else None
        filterFunc = list(filter(lambda x: filterByDistancia(x, latitudCliente, longitudCliente), PROVIDERS))
        return jsonify(filterFunc), 200
    except Exception as e:
        return {'error': str(e)}, 500

@app.route('/providers/<int:provider_id>', methods=['GET'])
def provider_profile(provider_id):
    provider = find_provider(provider_id)
    if provider is not None:
        return jsonify(provider), 200
    return jsonify({'error': f"no se pudo encontrar el proveedor {provider_id}"}), 500


@app.route('/users/<string:username>', methods=['PATCH'])
def getUser(username):
    try:
        data = request.get_json()
        token = data["token"]
        print(f"Token recibido: {token}")
        for user in USERS:
            if user["username"] == username:
                user["token"] = token
                return jsonify({'message': 'Login successful'}), 200
        return jsonify({'message': 'Login error'}), 403
    except Exception as e:
        return {'error': str(e)}, 500

@app.route('/users/<string:username>', methods=['GET'])
def refreshToken(username):
    try:
        for user in USERS:
            if user["username"] == username:
                return jsonify(user), 200
        return jsonify({'message': 'Login error'}), 403
    except Exception as e:
        return {'error': str(e)}, 500

@app.route('/users', methods=['GET'])
def listUsers():
    return jsonify({"users": USERS}), 200


def find_provider(id):
    return next((provider for provider in PROVIDERS if provider["id"] == id), None)

def find_user(username):
    return next((user for user in USERS if user["username"] == username), None)

def enviar_notificacion(username, provider):
    user = find_user(username)
    token = user["token"] if user and "token" in user else None
    provider_name = provider["nombre"]
    provider_lastname = provider["apellido"]
    body = f"{provider_name} {provider_lastname} acepto tu reserva.\nHace click para coordinar la visita!"
    disponibilidad = ", ".join(provider["disponibilidad"])
    send_notification(body, provider["id"], provider["precio_visita"], disponibilidad, token)

"""""""""""""""""""""""""""""""""""""""RUTAS PARA RESERVAS"""""""""""""""""""""""""""""""""""""""

# Crea una reserva para el provider <provider_id>
@app.route('/providers/<int:provider_id>/reservas', methods=['POST'])
def create_reserva(provider_id):
    try:
        data = request.get_json()
        print(f"Data limpia: {data}")
        data["id"] = str(uuid.uuid4())
        data["provider_id"] = provider_id
        data["accepted"] = False
        print(f"Data modificada: {data}")
        RESERVAS.append(data)
        date = data["date"]
        return jsonify({"message": f"Se creo una solicitud para el dia {date}"}), 200
    except Exception as e:
        return {'error': str(e)}, 500



# Lista las reservas PENDIENTES del provider <provider_id>
@app.route('/providers/<int:provider_id>/reservas/pendientes', methods=['GET'])
def get_reservas(provider_id):
    try:
        reservas_provider = list(filter(lambda reserva: reserva["provider_id"] is provider_id and not reserva["accepted"], RESERVAS))
        return jsonify({"reservas": reservas_provider}), 200
    except Exception as e:
        return {'error': str(e)}, 500

# Lista las reservas ACEPTADAS del provider <provider_id>
@app.route('/providers/<int:provider_id>/reservas/dates', methods=['GET'])
def get_accepted_dates(provider_id):
    try:
        reservas = list(filter(lambda reserva: reserva["provider_id"] is provider_id and reserva["accepted"], RESERVAS))
        fechas = [reserva["date"] for reserva in reservas]
        return jsonify(fechas), 200
    except Exception as e:
        return {'error': str(e)}, 500

# Acepta la reserva <reserva_id>
@app.route('/providers/reservas/<string:reserva_id>', methods=['PATCH'])
def accept_reserva(reserva_id):
    try:
        reserva = next((reserva for reserva in RESERVAS if reserva["id"] == reserva_id), None)
        if reserva is not None:
            username = reserva["username"]
            provider = find_provider(reserva["provider_id"])
            enviar_notificacion(username, provider)
            reserva["accepted"] = True
            return jsonify({"message": f"aceptaste la reserva de  {username}"}), 200
        return jsonify({'error': f"no se pudo encontrar la reserva con id {reserva_id}"}), 402
    except Exception as e:
        return {'error': str(e)}, 500

# Lista TODAS las reservas de todos los proveedores
@app.route('/providers/reservas', methods=['GET'])
def list_reservas():
    return jsonify({"reservas": RESERVAS}), 200

if __name__ == '__main__':
    app.run(debug=True)
