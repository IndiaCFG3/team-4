from django.urls import path
from . import views

urlpatterns = [
    path('api/class', views.GenerateClassPdf.as_view(), name='GenerateClassPdf'),
    path('api/student/<int:id>', views.GenerateStudentPdf.as_view(), name='GenerateStudentPdf')
]