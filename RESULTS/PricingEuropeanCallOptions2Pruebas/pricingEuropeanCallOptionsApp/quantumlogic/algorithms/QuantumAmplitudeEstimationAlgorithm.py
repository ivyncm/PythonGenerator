# -*- coding: utf-8 -*-
import qiskit, qiskit.circuit, qiskit_finance.circuit

from pricingEuropeanCallOptionsApp.businesslogic.entities import EuropeanCallProblem
from pricingEuropeanCallOptionsApp.businesslogic.entities import EuropeanCallObjective
from quantumCircuits.Grover import Grover

class QuantumAmplitudeEstimationAlgorithm():
	
	def __init__(self):
		self.problem = EuropeanCallProblem
		self.objective = EuropeanCallObjective
		self.numQubits = int()
		self.Grover = Grover()
	
	def getCircuitImage(self):
		pass
	
	def getEuropeanCallQuantumCricuit(self) -> qiskit.circuit.QuantumCircuit:
		pass
	
	def getproblem(self):
		return self.problem
	
	def setproblem(self, value):
		self.problem = value
	
	def getobjective(self):
		return self.objective
	
	def setobjective(self, value):
		self.objective = value
	
	def getnumQubits(self):
		return self.numQubits
	
	def setnumQubits(self, value):
		self.numQubits = value
	
	def getGrover(self):
		return self.Grover
	
	def setGrover(self, value):
		self.Grover = value
	

