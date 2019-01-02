/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.library;

import command.framework.Command;
import filesystem.Directory;
import interfaces.IDrive;
import interfaces.IOutputter;

/**
 *
 * @author Rumah
 */
class CmdExit extends Command {
    public CmdExit(String name, IDrive drive) {
        super(name, drive);
    }
    
    @Override
    public void execute(IOutputter outputter) {
        System.exit(0);
    }
}
