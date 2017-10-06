from django.http import HttpResponse, JsonResponse
from django.views.decorators.csrf import csrf_exempt
from rest_framework.renderers import JSONRenderer
from rest_framework.parsers import JSONParser
from food.models import Food
from food.serializers import FoodSerializer

# Create your views here.
def index(request):
    return HttpResponse("Hello Haasil")

@csrf_exempt
def food_list(request):
    """
    List all food, or create a new entry.
    """
    if request.method == 'GET':
        foods = Food.objects.all()
        serializer = FoodSerializer(foods, many=True)
        return JsonResponse(serializer.data, safe=False)
    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = FoodSerializer(data=data)
        if serializer.is_valid():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)

@csrf_exempt
def food_detail(request, pk):
    """
    Retrieve, update or delete a code food.
    """
    try:
        food = Food.objects.get(pk=pk)
    except Food.DoesNotExist:
        return HttpResponse(status=404)

    if request.method == 'GET':
        serializer = FoodSerializer(food)
        return JsonResponse(serializer.data)

    elif request.method == 'PUT':
        data = JSONParser().parse(request)
        serializer = FoodSerializer(food, data=data)
        if serializer.is_valid():
            serializer.save()
            return JsonResponse(serializer.data)
        return JsonResponse(serializer.errors, status=400)

    elif request.method == 'DELETE':
        food.delete()
        return HttpResponse(status=204)
