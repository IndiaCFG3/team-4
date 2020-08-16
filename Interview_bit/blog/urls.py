from django.urls import path
from .models import Bloger
from . import views
urlpatterns = {
    path('', views.index, name='index'),
    # path('', views.work_placefilter({'bloger.work_place':'workplace'}))
}