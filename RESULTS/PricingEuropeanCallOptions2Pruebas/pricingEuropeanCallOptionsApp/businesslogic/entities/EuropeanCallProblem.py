# -*- coding: utf-8 -*-
import qiskit, qiskit.circuit, qiskit_finance.circuit


class EuropeanCallProblem():
	
	def __init__(self):
		self.NUM_UNCERTAINTY_QUBITS = int(3)
		self.uncertaintyModel = qiskit_finance.circuit.library.LogNormalDistribution()
	
	def createDistribution(self, spotPrice: float, volatility: float, anualRate: float, maturityDays: int):
		pass
	
	def getUncertaintyModel(self) -> qiskit_finance.circuit.library.LogNormalDistribution:
		pass
	
	def getNUM_UNCERTAINTY_QUBITS(self):
		return self.NUM_UNCERTAINTY_QUBITS
	
	def setNUM_UNCERTAINTY_QUBITS(self, value):
		self.NUM_UNCERTAINTY_QUBITS = value
	
	def getuncertaintyModel(self):
		return self.uncertaintyModel
	
	def setuncertaintyModel(self, value):
		self.uncertaintyModel = value
	

