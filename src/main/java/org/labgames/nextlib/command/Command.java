package org.labgames.nextlib.command;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.labgames.nextlib.permissions.PermissionProvider;
import org.labgames.nextlib.permissions.SubPermission;

/**
 * Normal Command witch also uses extra permission features.
 */
public class Command extends net.minestom.server.command.builder.Command {

    /**
     * The base permissions of this command
     */
    protected String permission;

    /**
     * The permission provider for this command
     * @see SubPermission
     */
    protected SubPermission provider;

    /**
     * Creates a new command.
     * Default permissions the extension's permissions with the command name as a subpermission.
     *
     * @param provider PermissionsProvider, automatically set in the Extension class.
     * @param name Name of the command SubPermission will be automatically created with this name.
     * @param aliases Nullable, aliases to the command that the CommandSenders can also run to execute the command.
     *<br>
     *                <br>
     * See also:<br>
     * {@link Command#setPermission(String)}
     */
    public Command(@NotNull PermissionProvider provider, @NotNull String name, @Nullable String... aliases) {
        super(name, aliases);
        this.provider = new SubPermission(provider, name);
        setPermission(name);
        setCondition(this::defaultCondition);
    }

    /**
     * Sets the subcommand permission.
     * @param permission The permission to be used
     */
    public void setPermission(String permission) {
        this.permission = permission;
        updatePermissions();
    }

    /**
     * Set the required permissions level required for bypassing permissions.
     * @param level The new op level
     */
    public void setPermissionLevel(int level) {
        provider.updatePermissions(level, permission);
    }

    /**
     * Updates the subpermission permissions with the new command permissions.
     */
    public void updatePermissions() {
        provider.updatePermissions(permission);
    }

    /**
     * Gives the sender access if it is either a console or if it has the command permission.
     */
    protected boolean defaultCondition(CommandSender sender, String command) {
        return sender instanceof ConsoleSender || provider.hasPermission((Player) sender);
    }
}

