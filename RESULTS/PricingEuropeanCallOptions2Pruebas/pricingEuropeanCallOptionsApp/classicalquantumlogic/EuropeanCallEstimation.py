# -*- coding: utf-8 -*-
import qiskit, qiskit.circuit, qiskit_finance.circuit


class EuropeanCallEstimation():
	
	def __init__(self):
		self.result = qiskit.algorithms.IterativeAmplitudeEstimationResult()
		self.confidenceInterval = float()
	
	def showResults(self):
		pass
	
	def getresult(self):
		return self.result
	
	def setresult(self, value):
		self.result = value
	
	def getconfidenceInterval(self):
		return self.confidenceInterval
	
	def setconfidenceInterval(self, value):
		self.confidenceInterval = value
	

