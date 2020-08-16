from django.urls import path
from . import views

urlpatterns = [
    path('api/', views.fetch_pdf, name='fetch_pdf')
]