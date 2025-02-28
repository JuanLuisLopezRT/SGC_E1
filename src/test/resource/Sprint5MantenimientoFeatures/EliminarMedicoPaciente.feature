


@tag
Feature: Creación de una relacion Medico-Paciente

  @tag1
  Scenario Outline: Como administrador quiero crear una relacion Medico-Paciente (Web)
    Given ClienteHttpMedicoPaciente
    Given Envio peticion crear MedicoPaciente dni-user "<dni-user>", dni-medico "<dni-medico>", Response "<Response>"
    When borro la relacion dni-user "<dni-user>", dni-medico "<dni-medico>", Response "<Response>"
Examples:
    | dni-user   | dni-medico  |  Response  |
    |97637789Y   |65278762R    |    OK      |
    |            |65278762R    | Error      |
    |97637789Y   |             | Error      |
    |            |             | Error      |
    |97637789Y   |05707785     | Error      |
    |057266904N  |65278762R    | Error      |
    |97637789Y   |057077852J   | Error      |





