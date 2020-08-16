from django.shortcuts import render
from django.http import HttpResponse
from django.template import loader
from .models import Bloger
# Create your views here.
def index(request):
    Template =loader.get_template('indexpage.html')
    context={
        'retailer':Bloger.objects.all()
    }
    return HttpResponse(Template.render(context))
# def selectfilter(request):
#     Template = loader.get_template('indexpage.html')
#     context = {
#         'retailer': Bloger.objects.filter(selected="Selected")
#     }
#     return HttpResponse(Template.render(context))
# def work_placefilter(request,workplace):
#     Template = loader.get_template('indexpage.html')
#     context = {
#         'retailer': Bloger.objects.filter(work_place__icontains=workplace)
#     }
#     return HttpResponse(Template.render(context))