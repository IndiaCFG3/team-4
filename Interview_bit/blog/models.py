from django.db import models

from django.contrib.auth.models import User
# Create your models here.
#
# class Question(models.Model):
#     question_text = models.CharField(max_length=200)
#     pub_date = models.DateTimeField('date published')
#
#
# class Choice(models.Model):
#     question = models.ForeignKey(Question, on_delete=models.CASCADE)
#     choice_text = models.CharField(max_length=200)
#     votes = models.IntegerField(default=0)


class Bloger(models.Model):
    bloger_name = models.CharField(max_length=30)
    work_place = models.CharField(max_length=60)
    work_post = models.TextField()
    selected = models.CharField(max_length=60)
    preparation = models.TextField()
    salary = models.IntegerField()
    interview_exp=models.TextField()
    author = models.ForeignKey(User, on_delete=models.CASCADE)

    def __str__(self):
        return self.bloger_name

    #
    # def __init__(self,vendor_name,author):
    #     self.vendor_name=vendor_name
    #     self.author=author


